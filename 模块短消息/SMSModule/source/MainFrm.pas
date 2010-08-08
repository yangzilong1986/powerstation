unit MainFrm;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls, cmGsmModule, DB, MemDS, DBAccess, Ora;

type
  TMainForm = class(TForm)
    Panel1: TPanel;
    logMemo: TMemo;
    btnStartStop: TButton;
    btnSetOptions: TButton;
    cmGsmModule1: TcmGsmModule;
    OraSession1: TOraSession;
    orqSend: TOraQuery;
    oraReceive: TOraQuery;
    orqSendID: TFloatField;
    orqSendTELECODE: TStringField;
    orqSendCODEC: TIntegerField;
    orqSendMSG: TStringField;
    orqSendSTATUS: TIntegerField;
    orqSendINSERTTIME: TDateTimeField;
    orqSendTAG: TStringField;
    oraReceiveID: TFloatField;
    oraReceiveTELECODE: TStringField;
    oraReceiveCODEC: TIntegerField;
    oraReceiveMSG: TStringField;
    oraReceiveRECEIVETIME: TDateTimeField;
    CheckTimer: TTimer;
    orqSent: TOraQuery;
    orqSentID: TFloatField;
    orqSentTELECODE: TStringField;
    orqSentCODEC: TIntegerField;
    orqSentMSG: TStringField;
    orqSentSTATUS: TIntegerField;
    orqSentINSERTTIME: TDateTimeField;
    orqSentTAG: TStringField;
    procedure btnStartStopClick(Sender: TObject);
    procedure btnSetOptionsClick(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure OraSession1BeforeConnect(Sender: TObject);
    procedure cmGsmModule1ReceiveSmsMsg(Sender: TObject;
      const OrgAddress: String; SCTime: TDateTime; dcs: Byte;
      const Msg: String);
    procedure CheckTimerTimer(Sender: TObject);
    procedure cmGsmModule1SentSmsMsgEx(Sender: TObject;
      const Sequence: Double; const SmscAddress, DestAddress: String;
      dcs: Byte; const Msg: String; const Success: Boolean);
  private
    Line: integer;
    procedure SetbtnStartState;
    procedure SetbtnStopState;
    procedure Start;
    procedure Stop;
    procedure doRun;
    procedure doStop;
    function isBtnOnStartState: Boolean;
    procedure AddLog(const Msg: AnsiString);
  public
    { Public declarations }
  end;

var
  MainForm: TMainForm;

implementation

uses SetOptionFrm, uoptions, uBCDHelper;

{$R *.dfm}

{ TMainForm }

procedure TMainForm.SetbtnStartState;
begin
  btnStartStop.Tag := 1;
  btnStartStop.Caption := '停止';
end;

procedure TMainForm.SetbtnStopState;
begin
  btnStartStop.Tag := 0;
  btnStartStop.Caption := '运行';
end;

procedure TMainForm.Start;
begin
  SetbtnStartState;
  doRun;
end;

procedure TMainForm.Stop;
begin
  SetbtnStopState;
  doStop;
end;

procedure TMainForm.doRun;
begin
  cmGsmModule1.Port := Options.CommPort;
  cmGsmModule1.Baudrate := Options.CommBps;
  cmGsmModule1.Open;
  CheckTimer.Enabled := true;
end;

procedure TMainForm.doStop;
begin
  cmGsmModule1.Close;
  CheckTimer.Enabled := false;
  OraSession1.Close;
end;

function TMainForm.isBtnOnStartState: Boolean;
begin
  Result := btnStartStop.Tag=1;
end;

procedure TMainForm.btnStartStopClick(Sender: TObject);
begin
  if isBtnOnStartState() then
    self.Stop
  else
    self.Start;
end;

procedure TMainForm.btnSetOptionsClick(Sender: TObject);
var
  setForm: TSetOptionForm;
begin
  setForm := TSetOptionForm.Create(nil);
  try
    if setForm.ShowModal=mrOK then
    begin
      self.doStop;
      self.doRun;
    end;
  finally
    setForm.Free;
  end;
end;

procedure TMainForm.FormShow(Sender: TObject);
begin
  if not FileExists('.\Options.ini') then
    btnSetOptionsClick(nil);
  if FileExists('.\Options.ini') then
  begin
    btnStartStop.Enabled := true;
    btnStartStopClick(nil);
  end;
end;

procedure TMainForm.OraSession1BeforeConnect(Sender: TObject);
begin
  OraSession1.Options.Net := true;
  OraSession1.Server := Options.OraIPAddress+':'+IntToStr(Options.OraIPPort)+
        ':'+Options.OraSID;
  OraSession1.Username := Options.OraUser;
  OraSession1.Password := Options.OraPwd;
end;

procedure TMainForm.cmGsmModule1ReceiveSmsMsg(Sender: TObject;
  const OrgAddress: String; SCTime: TDateTime; dcs: Byte;
  const Msg: String);
begin
  oraReceive.Open;
  oraReceive.Insert;
  oraReceive.FieldByName('id').AsInteger := 1;
  oraReceive.FieldByName('telecode').AsString := OrgAddress;
  oraReceive.FieldByName('codec').AsInteger := dcs;
  if dcs<>4 then
    oraReceive.FieldByName('msg').AsString := Msg
  else
    oraReceive.FieldByName('msg').AsString := TBcdHelper.BinStr2CStr(Msg,'');
  oraReceive.FieldByName('receiveTime').AsDateTime := Now;
  oraReceive.Post;
  AddLog('收到来自<'+OrgAddress+'>发送的消息,dcs='+IntToStr(dcs)+': '+Msg);
end;

procedure TMainForm.CheckTimerTimer(Sender: TObject);
var
  sequence: Double;
  telecode: AnsiString;
  codec: Byte;
  Msg: AnsiString;
  p,q: integer;

  function nextPos(const msg: AnsiString; pos: integer): integer;
  var
    b: Byte;
    i: integer;
    p: integer;
  begin
    i := 0;
    p := pos;
    while (i<4) and ((p)<=length(msg)) do
    begin
      b := Ord(msg[p]);
      if b>=$80 then
        p := p+2
      else
        p := p+1;
      i := i+1;
    end;
    Result := p;
    if i = 0 then
      Result := result+1;
  end;

begin
  CheckTimer.Enabled := false;
  orqSend.Open;
  try
    orqSend.First;
    while not orqSend.Eof do
    begin
      sequence := orqSend.fieldByName('ID').AsFloat;
      telecode := orqSend.FieldByName('Telecode').AsString;
      codec := orqSend.fieldByName('codec').AsInteger;
      msg := orqSend.FieldByName('msg').AsString;
      if codec<>4 then
      begin
        p := 1;
        q := nextPos(msg,p);
        while q<=Length(Msg)+1 do
        begin
          cmGsmModule1.SendSMSEx(sequence,Options.Smsc,Telecode,codec,Copy(Msg,p,q-p));
          p := q;
          q := nextPos(msg,p);
        end;
      end
      else
      begin
        cmGsmModule1.SendSMSEx(sequence,Options.Smsc,Telecode,codec,TBcdHelper.CStr2BinStr(Msg));
        AddLog('获取发送请求，向<'+Telecode+'>发送消息,dcs='+IntToStr(codec)+': '+Msg);
      end;

      orqSend.Edit;
      orqSend.FieldByName('status').AsInteger := 1;
      orqSend.Post;
      orqSend.Next;
    end;
  finally
    orqSend.Close;
    CheckTimer.Enabled := true;
  end;
end;

procedure TMainForm.cmGsmModule1SentSmsMsgEx(Sender: TObject;
  const Sequence: Double; const SmscAddress, DestAddress: String;
  dcs: Byte; const Msg: String; const Success: Boolean);
function GetMsg: AnsiString;
begin
  if dcs=4 then
    Result := TBcdHelper.BinStr2CStr(Msg)
  else
    Result := Msg;
end;
begin
  orqSent.ParamByName('id').AsFloat := Sequence;
  orqSent.Open;
  if orqSent.RecordCount<>0 then
  begin
    orqSent.Edit;
    if success then
      orqSent.FieldByName('STATUS').AsInteger := 2
    else
      orqSent.FieldByName('STATUS').AsInteger := 3;
    orqSent.Post;
  end;
  orqSent.Close;
  if success then
    AddLog('向<'+DestAddress+'>发送消息成功,dcs='+IntToStr(dcs)+': '+GetMsg)
  else
    AddLog('向<'+DestAddress+'>发送消息失败,dcs='+IntToStr(dcs)+': '+GetMsg);
end;

procedure TMainForm.AddLog(const Msg: AnsiString);
begin
  Line := Line+1;
  if Line>500 then
  begin
    Line := 0;
    logMemo.Lines.Clear;
  end;
  logMemo.Lines.Add(msg);
end;

end.
