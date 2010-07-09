unit uSerialCommunictor;

interface

uses
  ExtCtrls, uCiaComport;

type
  TOnReceiveDataEvent = procedure (Sender: TObject; const Data: AnsiString) of Object;
  TSerialCommunicator = class
  private
    FComPort: TCiaComPort;
    FTimeoutTimer: TTimer;
    FTimeouted: Boolean;
    FReadBuff: AnsiString;
    FOnReceiveDataEvent: TOnReceiveDataEvent;
    FIsRtuCommunication: Boolean;
    procedure Open;
    procedure Close;
    procedure CheckReadBuff;
  protected
    procedure ComPortDataAvailable(Sender: TObject);
    procedure FTimeoutTimerTimer(Sender: TObject);
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    procedure Reset;
    procedure Send(const DataStr: AnsiString;
      OnReceiveDataEvent: TOnReceiveDataEvent;
      IsRtuCommunication: Boolean);
  end;

  function SerialCommunicator: TSerialCommunicator;

implementation

uses uOptions, uMeter645Packet, PmAppPacket;

var
  FSerialCommunicator: TSerialCommunicator;

function SerialCommunicator: TSerialCommunicator;
begin
  if FSerialCommunicator=nil then
    FSerialCommunicator := TSerialCommunicator.Create;
  Result := FSerialCommunicator;
end;

{ TSerialCommunicator }

procedure TSerialCommunicator.AfterConstruction;
begin
  inherited;
  FComPort := TCiaComPort.Create(nil);
  FComPort.OnDataAvailable := ComPortDataAvailable;
  FTimeoutTimer := TTimer.Create(nil);
  FTimeoutTimer.Enabled := false;
  FTimeoutTimer.OnTimer := FTimeoutTimerTimer;
end;

procedure TSerialCommunicator.CheckReadBuff;
begin

end;

procedure TSerialCommunicator.Close;
begin
  FTimeoutTimer.Enabled := false;
  FComPort.Open := false;
  FReadBuff := '';
end;

procedure TSerialCommunicator.ComPortDataAvailable(Sender: TObject);
var
  temp: AnsiString;
begin
  temp := FComPort.ReceiveStr;
  if FTimeouted then
    exit;
  FReadBuff := FReadBuff + temp;
  CheckReadBuff;
end;

destructor TSerialCommunicator.Destroy;
begin
  Close;
  FTimeoutTimer.Free;
  FComPort.Free;
  inherited;
end;

procedure TSerialCommunicator.FTimeoutTimerTimer(Sender: TObject);
begin
  FTimeouted := true;
  FReadBuff := '';
  FTimeoutTimer.Enabled := false;
end;

procedure TSerialCommunicator.Open;
begin
  if self.FComPort.Open then
    exit;

  FTimeoutTimer.Interval := Options.Timeout;
  FReadBuff := '';
  FComPort.Port := Options.CommPort;
  FComPort.Baudrate := Options.CommBps;
  FComPort.ByteSize := 8;
  FComPort.StopBits := sbOne;
  FComPort.LineMode := false;
  FComPort.Parity := TParity(Options.Parity);
  FComPort.Open := true;
end;

procedure TSerialCommunicator.Reset;
begin
  Close;
  Open;
end;

procedure TSerialCommunicator.Send(const DataStr: AnsiString;
  OnReceiveDataEvent: TOnReceiveDataEvent;
  IsRtuCommunication: Boolean);
begin
  FOnReceiveDataEvent := OnReceiveDataEvent;
  FIsRtuCommunication := IsRtuCommunication;
  Open;
  FReadBuff := '';
  FComPort.SendStr(DataStr);
  FTimeoutTimer.Enabled := true;
end;

initialization
  FSerialCommunicator := nil;

finalization
  if FSerialCommunicator<>nil then
    FSerialCommunicator.Free;

end.
