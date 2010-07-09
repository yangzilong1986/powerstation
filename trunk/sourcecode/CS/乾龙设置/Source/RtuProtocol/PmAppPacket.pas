unit PmAppPacket;

//��·�û����ݲ��װ
//Ϊ�˱������壬Ӧ��ÿ�ζ���������һ�����ݱ�ʶ
//ע�⣡��������  ������Щafn��Ҫ��Ϣ��֤����ȷ��

interface

uses
  SysUtils, Classes, StrUtils, PmHelper, PmPacket;

type
  TPmAppAFN = (afnQuerenOrFouren=0,afnFuwei=1,afnLianluJiance=2,
               afnZhongjiMingling=3,afnShezhiCanshu=4,afnKongzhiMingling=5,
               afnRenzheng=6,afnChaxunCanshu=$0A,
               afnYileishuju=$0C,afnErleiShuju=$0D,afnSanleiShuju=$0E,
               afnWenjianChuanshu=$0F,afnShujuZhuanfa=$10);

  TPmAppSeq = class
  private
    FValue: Byte;
    procedure SetValue(const Value: Byte);
    function GetJieshuZheng: Boolean;
    function GetKaishizheng: Boolean;
    function GetSeq: Byte;
    function GetShijianYouxiao: Boolean;
    function GetXuyaoQueren: Boolean;
    procedure SetJieshuZheng(const Value: Boolean);
    procedure SetKaishizheng(const Value: Boolean);
    procedure SetSeq(const Value: Byte);
    procedure SetShijianYouxiao(const Value: Boolean);
    procedure SetXuyaoQueren(const Value: Boolean);
  public
    property Value: Byte read FValue write SetValue;
    property ShijianYouxiao: Boolean read GetShijianYouxiao write SetShijianYouxiao;
    property IsKaishizheng: Boolean read GetKaishizheng write SetKaishizheng;
    property IsJieshuZheng: Boolean read GetJieshuZheng write SetJieshuZheng;
    property XuyaoQueren: Boolean read GetXuyaoQueren write SetXuyaoQueren;
    property Seq: Byte read GetSeq write SetSeq;
    function CStr: AnsiString;
  end;

  //DA����ϢԪ���(DA2)��1-255)�����ڱ���(DA1)(8λ����ʾ1-8����ϢԪ��
  //DA1����λΪ����ģ�DA2��ֵ�����
  //����ʾ8����ϢԪ
  //�ر�ģ�DA2=DA1=0 ʱ��ʾp0
  TPmAppDA = class
  private
    FDA2: Byte;
    FDA1: Byte;
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
    function GetIsP0: Boolean;
  public
    property Value: AnsiString read GetValue write SetValue;
    function GetPsList: TList;
    property IsP0: Boolean read GetIsP0;
    procedure SetP0;
    procedure AddPx(const Px: Word);
  end;

  //DT����Ϣ��
  //��Ϣ�����ϢԪ�ı��뷽�����ƣ���������DA2��1��ʼ���룬DT1��0��ʼ����
  TPmAppDT = class
  private
    FDT1: Byte;
    FDT2: Byte;
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
  public
    property Value: AnsiString read GetValue write SetValue;
    function GetFsList: TList;
    procedure AddFx(const Fx: Word);
  end;

  //TPmAppPW�����ڷ�װ��֤���㷨����ΪĿǰ�㷨δ֪��������Ҫ��δ����չ
  TPmAppPW = class
  private
    FValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
    function GetValue: AnsiString;
  public
    property Value: AnsiString read GetValue write SetValue;
  end;

  TPmAppEC = class
  private
    FEC1: Byte;
    FEC2: Byte;
    procedure SetZhongyanShijianJishuqi(const Value: Byte);
    procedure SetYibanShijianJishuqi(const Value: Byte);
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
  public
    property ZhongyanShijianJishuqi: Byte read FEC1 write SetZhongyanShijianJishuqi;
    property YibanShijianJishuqi: Byte read FEC2 write SetYibanShijianJishuqi;
    property Value: AnsiString read GetValue write SetValue;
    function CStr: AnsiString;
  end;

  //ʱ���ǩ
  TPmAppTp = class
  private
    FPFC: Byte;
    FFasongShibiao: TPmData16;
    FYunxuShiyian: Byte;
    procedure SetQidongzhengxuhaoJishuqi(const Value: Byte);
    procedure SetYunxuShiyian(const Value: Byte);
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    property QidongzhengxuhaoJishuqi: Byte read FPFC write SetQidongzhengxuhaoJishuqi;
    property FasongShibiao: TPmData16 read FFasongShibiao;
    property YunxuShiyian: Byte read FYunxuShiyian write SetYunxuShiyian;
    property Value: AnsiString read GetValue write SetValue;
    function CStr: AnsiString;
  end;

  TPmAppDataUnit = class
  private
    FValue: AnsiString;
    function GetAsValue: AnsiString;
    procedure SetAsValue(const Value: AnsiString);
  public
    property Value: AnsiString read GetAsValue write SetAsValue;
    
    class function MakeDADT(Pn,Fn: Word): AnsiString;
  end;

  TPmAppPacket = class
  private
    FLinkLayerPacket: TPmLinkLayerPacket;
    FData: AnsiString;
    FSeq: TPmAppSeq;
    FAFN: Byte;
    FEC: TPmAppEC;
    FPW: TPmAppPW;
    FTp: TPmAppTp;
    procedure SetAFN(const Value: Byte);
    function GetAddress: TPmAddress;
    function GetControlcode: TPmControlCode;
    function GetAsBinStr: AnsiString;
    procedure SetAsBinStr(const Value: AnsiString);
    function HavePW(const afn: Byte): boolean;
    procedure SetData(const Value: AnsiString);
    function AFNName(afn: Byte): AnsiString;
  protected
    procedure FillLinklayerData; //Ӧ�ò���Ҫ��appdata�����data��
    procedure ParseLinklayerData(const Data: AnsiString); //Ӧ�ò㽫data�������Լ�������
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    property AFN: Byte read FAFN write SetAFN;
    property SEQ: TPmAppSeq read FSeq;
    property EC: TPmAppEC read FEC;
    property PW: TPmAppPW read FPW;
    property Tp: TPmAppTp read FTp;
    property Data: AnsiString read FData write SetData;
    class function GetAppPacket(var DataStr: AnsiString): TPmAppPacket;
    property ControlCode: TPmControlCode read GetControlcode;  //��·������
    property Address: TPmAddress read GetAddress;  //��·��ַ��
    property AsBinStr: AnsiString read GetAsBinStr write SetAsBinStr;
    function CStr: AnsiString;
  end;

  InvalidDataUnitIDException = class (Exception)
  end;

  InvalidAppPackDataLengthException = class(Exception)
  end;

  TRecvPmAppPacketEvent = procedure (Sender: TObject; const pack: TPmAppPacket;
                                     hasHouxuzhen: Boolean) of Object;

implementation

uses uBCDHelper;

{ TPmAppSeq }

function TPmAppSeq.CStr: AnsiString;
  function BooleanToStr(b: Boolean): AnsiString;
  begin
    if b then
      Result := '1'
    else
      Result := '0';
  end;
begin
  Result := 'TpV='+BooleanToStr(ShijianYouxiao)+
            '; FIR='+BooleanToStr(IsKaishizheng)+
            '; FIN='+BooleanToStr(IsJieshuzheng)+
            '; CON='+BooleanToStr(XuyaoQueren)+
            '; Seq='+IntToStr(seq);
end;

function TPmAppSeq.GetJieshuZheng: Boolean;
begin
  Result := (Fvalue and $20)=$20;
end;

function TPmAppSeq.GetKaishizheng: Boolean;
begin
  Result := (FValue and $40)=$40;
end;

function TPmAppSeq.GetSeq: Byte;
begin
  Result := FValue and $0F;
end;

function TPmAppSeq.GetShijianYouxiao: Boolean;
begin
  Result := (FValue and $80)=$80;
end;

function TPmAppSeq.GetXuyaoQueren: Boolean;
begin
  Result := (FValue and $10)=$10;
end;

procedure TPmAppSeq.SetJieshuZheng(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $20
  else
    FValue := FValue and $DF;
end;

procedure TPmAppSeq.SetKaishizheng(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $40
  else
    FValue := FValue and $BF;
end;

procedure TPmAppSeq.SetSeq(const Value: Byte);
begin
  FValue := (FValue and $F0)+(Value and $0F);
end;

procedure TPmAppSeq.SetShijianYouxiao(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $80
  else
    FValue := FValue and $7F;
end;

procedure TPmAppSeq.SetValue(const Value: Byte);
begin
  FValue := Value;
end;

procedure TPmAppSeq.SetXuyaoQueren(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $10
  else
    FValue := FValue and $EF;
end;

{ TPmAppDA }

function TPmAppDA.GetIsP0: Boolean;
begin
  Result := (FDA1=0) and (FDA2=0);
end;

function TPmAppDA.GetValue: AnsiString;
begin
  Result := Chr(FDA1)+Chr(FDA2);
end;

function TPmAppDA.GetPsList: TList;
var
  lst: TList;
  i: Byte;
  b: Byte;
begin
  lst := TList.Create;

  for i := 0 to 7 do
  begin
    b := 1 shl i;
    if ((FDA1 and b)=b) then
      lst.Add(Pointer((FDA2-1)*8+i+1));
  end;
  if lst.Count=0 then
    lst.Add(Pointer(0));
  Result := lst;
end;

procedure TPmAppDA.SetP0;
begin
  FDA1 := 0;
  FDA2 := 0;
end;

procedure TPmAppDA.SetValue(const Value: AnsiString);
begin
  if Length(Value)=2 then
  begin
    FDA1 := Ord(Value[1]);
    FDA2 := Ord(Value[2]);
  end
  else
    raise Exception.Create('��Ϣ���ʶ��������2�ֽ�');
end;

procedure TPmAppDA.AddPx(const Px: Word);
var
  da1,da2: Byte;
begin
  if Px=0 then
    self.SetP0
  else
  begin
    da1 := 1 shl ((Px-1) mod 8);
    da2 := ((Px-1) div 8)+1;
    if FDA2=0 then
      FDA2 := da2;
    if FDA2=da2 then
      FDA1 := FDA1 or da1;
  end;
end;

{ TPmAppDT }

procedure TPmAppDT.AddFx(const Fx: Word);
var
  dt1,dt2: Byte;
begin
  dt1 := 1 shl ((Fx-1) mod 8);
  dt2 := (Fx-1) div 8;
  if FDT1=0 then //FDT1ֻ�г�ʼʱ��0��ֻҪ���ӹ�Fx���ͱ�ɷ�0
    FDT2 := dt2;
  if FDT2=dt2 then
    FDT1 := FDT1 or dt1;
end;

function TPmAppDT.GetFsList: TList;
var
  lst: TList;
  i: Byte;
  d1: Byte;
begin
  lst := TList.Create;
  for i := 0 to 7 do
  begin
    d1 := 1 shl i;
    if ((FDT1 and d1)=d1) then
      lst.Add(Pointer(FDT2*8+i+1));
  end;
  Result := lst;
end;

function TPmAppDT.GetValue: AnsiString;
begin
  Result := Chr(FDT1)+Chr(FDT2);
end;

procedure TPmAppDT.SetValue(const Value: AnsiString);
begin
  if Length(Value)=2 then
  begin
    FDT1 := Ord(Value[1]);
    FDT2 := Ord(Value[2]);
  end
  else
    raise Exception.Create('��Ϣ���ʶ��������2�ֽ�');
end;

{ TPmAppPW }

function TPmAppPW.GetValue: AnsiString;
begin
  if Length(FValue)>16 then
    Result := Copy(FValue,1,16)
  else if Length(FValue)=16 then
    Result := FValue
  else
    Result := FValue+DupeString(#0,16-Length(FValue));
  Result := #1#2#3#4#5#6#7#8#9#0#$A#$B#$C#$D#$E#$F;
end;

procedure TPmAppPW.SetValue(const Value: AnsiString);
begin
  FValue := Value;
end;

{ TPmAppEC }

function TPmAppEC.CStr: AnsiString;
begin
  Result := '��Ҫ�¼�������='+IntToStr(ZhongyanShijianJishuqi)+
            '; һ���¼�������='+IntToStr(YibanShijianJishuqi);
end;

function TPmAppEC.GetValue: AnsiString;
begin
  Result := Chr(FEC1)+Chr(FEC2);
end;

procedure TPmAppEC.SetValue(const Value: AnsiString);
begin
  if Length(Value)=2 then
  begin
    FEC1 := Ord(Value[1]);
    FEC2 := Ord(Value[2]); 
  end
  else
    raise Exception.Create('�¼�����������Ϊ2�ֽ�');
end;

procedure TPmAppEC.SetYibanShijianJishuqi(const Value: Byte);
begin
  FEC2 := Value;
end;

procedure TPmAppEC.SetZhongyanShijianJishuqi(const Value: Byte);
begin
  FEC1 := Value;
end;

{ TPmAppTp }

procedure TPmAppTp.AfterConstruction;
begin
  inherited;
  FFasongShibiao := TPmData16.Create;
end;

function TPmAppTp.CStr: AnsiString;
begin
  Result := '����֡֡��ż�����='+IntToStr(self.FPFC)+
            '; ����֡����ʱ��='+self.FFasongShibiao.CStr+
            '; �����ʹ�����ʱʱ��='+IntToStr(self.YunxuShiyian);
end;

destructor TPmAppTp.Destroy;
begin
  FFasongShibiao.Free;
  inherited;
end;

function TPmAppTp.GetValue: AnsiString;
begin
  Result := chr(FPFC)+FFasongShibiao.Value+chr(FYunxuShiyian);
end;

procedure TPmAppTp.SetQidongzhengxuhaoJishuqi(const Value: Byte);
begin
  FPFC := Value;
end;

procedure TPmAppTp.SetValue(const Value: AnsiString);
begin
  if length(Value)=6 then
  begin
    FPFC := Ord(Value[1]);
    FFasongShibiao.Value := Copy(Value,2,4);
    FYunxuShiyian := Ord(Value[6]);
  end
  else
    raise Exception.Create('Tp��ǩ�ĳ��ȱ�����6�ֽ�');
end;

procedure TPmAppTp.SetYunxuShiyian(const Value: Byte);
begin
  FYunxuShiyian := Value;
end;

{ TPmAppPacket }

procedure TPmAppPacket.AfterConstruction;
begin
  inherited;
  FSeq := TPmAppSeq.Create;
  FEC := TPmAppEC.Create;
  FPW := TPmAppPW.Create;
  FTp := TPmAppTp.Create;
  FLinkLayerPacket := TPmLinkLayerPacket.Create;
end;

destructor TPmAppPacket.Destroy;
begin
  FSeq.Free;
  FEC.Free;
  FPW.Free;
  FTp.Free;
  FLinkLayerPacket.Free;
  inherited;
end;

class function TPmAppPacket.GetAppPacket(
  var DataStr: AnsiString): TPmAppPacket;
var
  pack: TPmLinkLayerPacket;
begin
  pack := TPmLinkLayerPacket.GetPacket(DataStr);
  if pack<>nil then
  begin
    Result := TPmAppPacket.Create;
    Result.FLinkLayerPacket.Free;
    Result.FLinkLayerPacket := pack;
    Result.ParseLinklayerData(pack.Data);
  end
  else
    Result := nil;
end;

procedure TPmAppPacket.SetAFN(const Value: Byte);
begin
  FAFN := Value;
end;

function TPmAppPacket.GetAddress: TPmAddress;
begin
  Result := FLinkLayerPacket.Address;
end;

function TPmAppPacket.GetControlcode: TPmControlCode;
begin
  Result := FLinkLayerPacket.ControlCode;
end;

function TPmAppPacket.GetAsBinStr: AnsiString;
begin
  FillLinklayerData;
  Result := FLinkLayerPacket.AsBinStr;
end;

procedure TPmAppPacket.SetAsBinStr(const Value: AnsiString);
begin
  FLinkLayerPacket.AsBinStr := Value;
  ParseLinklayerData(self.FLinkLayerPacket.Data);
end;

procedure TPmAppPacket.FillLinklayerData;
var
  temp: AnsiString;
begin
  SEQ.XuyaoQueren := ((self.AFN=$1) or (self.afn=$4) or (self.afn=$5) or (self.AFN=$10));
  temp := Chr(FAFN)+Chr(FSeq.Value);
  temp := temp+self.FData;
  if self.ControlCode.IsShangxingzhen then
  begin
    if self.ControlCode.IsShangxingYaoqiufangwen then
      temp := temp+FEC.Value;
  end
  else
  begin
    if HavePW(FAFN) then
    temp := temp+FPW.Value;
  end;
  if FSeq.ShijianYouxiao then
    temp := temp+FTP.Value;
  self.FLinkLayerPacket.Data := temp;
end;

procedure TPmAppPacket.ParseLinklayerData(const Data: AnsiString);
var
  p,q: integer;
  len: integer;
begin
  len := Length(Data);
  p := 1;
  if p>len then
    raise InvalidAppPackDataLengthException.Create('�����쳣');
  AFN := Ord(Data[p]);
  p := p+1;
  if p>len then
    raise InvalidAppPackDataLengthException.Create('�����쳣');
  SEQ.Value := Ord(Data[p]);
  p := p+1;

  q := Length(Data);
  if Seq.ShijianYouxiao then
  begin
    q := q-5;
    if p>q then
      raise InvalidAppPackDataLengthException.Create('�����쳣');
    self.FTp.Value := Copy(Data,q,6);
    q := q-1;
  end;
  if self.ControlCode.IsShangxingzhen then
  begin
    if ControlCode.XiaxingZhenjishuYouxiao then
    begin
      q := q-1;
      if p>q then
        raise InvalidAppPackDataLengthException.Create('�����쳣');
      FEC.Value := Copy(Data,q,2);
      q := q-1;
    end;
  end
  else
  begin
    if HavePW(FAFN) then
    begin
      q := q-15;
      if p>q then
        raise InvalidAppPackDataLengthException.Create('�����쳣');
      FPW.Value := Copy(Data,q,16);
      q := q-1;
    end;
  end;

  FData := Copy(Data,p,q-p+1);
end;

function TPmAppPacket.HavePW(const afn: Byte): boolean;
begin
  case afn of
    1,4,5,6,$0F,$10:
      Result := true;
  else
    Result := false;
  end;
end;

procedure TPmAppPacket.SetData(const Value: AnsiString);
begin
  FData := Value;
end;

function TPmAppPacket.CStr: AnsiString;
var
  Temp: AnsiString;
begin
  Temp := '';
  Temp := Temp+'������: '+self.ControlCode.CStr+#$0D#$0A;
  Temp := Temp+'��ַ��: '+self.Address.CStr+#$0D#$0A;
  Temp := Temp+'AFN='+format('%2.2x',[self.AFN])+'  -'+AFNName(self.AFN)+#$0D#$0A;
  Temp := Temp+'֡������: '+self.SEQ.CStr+#$0D#$0A;
  Temp := Temp+'������:'+#$0D#$0A;
  Temp := Temp+TBcdHelper.BinStr2CStr(self.Data)+#$0D#$0A;
  Temp := Temp+'������Ϣ��:'+#$0D#$0A;
  if self.ControlCode.IsShangxingzhen then
  begin
    if self.ControlCode.IsShangxingYaoqiufangwen then
      temp := temp+'�¼�������: '+FEC.CStr+#$0D#$0A;
  end
  else
  begin
    if HavePW(FAFN) then
      temp := temp+'��Ϣ��֤���ֶ�: '+TBcdHelper.BinStr2CStr(FPW.Value,'')+#$0D#$0A;
  end;
  if FSeq.ShijianYouxiao then
    temp := temp+'ʱ���ǩ: '+TBcdHelper.BinStr2CStr(FTP.Value,'')+'-'+FTP.CStr;

  Result := Temp+#$0D#$0A;
end;

function TPmAppPacket.AFNName(afn: Byte): AnsiString;
begin
  case afn of
    $0: Result := 'ȷ��/����';
    $1: Result := '��λ';
    $2: Result := '��·�ӿڼ��';
    $3: Result := '�м�վ����';
    $4: Result := '���ò���';
    $5: Result := '��������';
    $6: Result := '�����֤����ԿЭ��';
    $7: Result := '����';
    $8: Result := '���󱻼����ն���������';
    $9: Result := '�����ն�����';
    $A: Result := '��ѯ����';
    $B: Result := '������������';
    $C: Result := '����һ�����ݣ�ʵʱ���ݣ�';
    $D: Result := '����������ݣ���ʷ���ݣ�';
    $E: Result := '�����������ݣ��¼����ݣ�';
    $F: Result := '�ļ�����';
    $10: Result := '����ת��';
  else
    Result := '����';
  end;
end;

{ TPmAppDataUnit }

function TPmAppDataUnit.GetAsValue: AnsiString;
begin
  Result := FValue;
end;

class function TPmAppDataUnit.MakeDADT(Pn, Fn: Word): AnsiString;
var
  da: TPmAppDA;
  dt: TPmAppDT;
begin
  da := TPmAppDA.Create;
  dt := TPmAppDT.Create;
  da.AddPx(Pn);
  dt.AddFx(Fn);
  Result := da.Value+dt.Value;
  da.Free;
  dt.Free;
end;

procedure TPmAppDataUnit.SetAsValue(const Value: AnsiString);
begin
  FValue := Value;
end;

end.
