unit uMeter645Packet;

interface

uses
  SysUtils, StrUtils;

type
  TMeter645ControlCode = class
  private
    FValue: Byte;
    function GetAsHexString: AnsiString;
    function GetGongNengma: Byte;
    function GetHasHouxuzheng: Boolean;
    function GetIsFachu: Boolean;
    function GetIsFanhui: Boolean;
    function GetIsYichang: Boolean;
    procedure SetAsHexString(const Value: AnsiString);
    procedure SetGongNengma(const Value: Byte);
    procedure SetHasHouxuzheng(const Value: Boolean);
    procedure SetIsFachu(const Value: Boolean);
    procedure SetIsFanhui(const Value: Boolean);
    procedure SetIsYichang(const Value: Boolean);
  public
    constructor Create; overload;
    property AsHexString: AnsiString read GetAsHexString write SetAsHexString;
    property IsFromZhuzhanToMeter: Boolean read GetIsFachu write SetIsFachu;
    property IsFromMeterToZhuzhan: Boolean read GetIsFanhui write SetIsFanhui;
    property IsYichang: Boolean read GetIsYichang write SetIsYichang;
    property HasHouxuzheng: Boolean read GetHasHouxuzheng write SetHasHouxuzheng;
    property GongNengma: Byte read GetGongNengma write SetGongNengma;
    property Value: Byte read FValue write FValue;
  end;

  TMeter645Address = class
  private
    FAsHexString: AnsiString;
    FShrinkDecimals: Byte;
    FShrinked: Boolean;
    function GetAsString: AnsiString;
    procedure SetAsHexString(const Value: AnsiString);
    procedure SetAsString(const Value: AnsiString);
    procedure SetShrinkDecimals(const Value: Byte);
    procedure SetShrinked(const Value: Boolean);
  public
    constructor Create; overload;
    property AsString: AnsiString read GetAsString write SetAsString;
    property AsHexString: AnsiString read FAsHexString write SetAsHexString;
    property ShrinkDecimals: Byte read FShrinkDecimals write SetShrinkDecimals;
    property Shrinked: Boolean read FShrinked write SetShrinked;
    function ShrinkAddress(const ShrinkDecimals: Byte): AnsiString;
    class function String2Address(const Str: AnsiString): AnsiString;
    class function Address2String(const BinStr: AnsiString): AnsiString;
  end;

  //AppData表示应用层数据，对于发送而言是没有+33之前的数据
  //对于接受而言是已经-33后的数据
  //FData是实际的包里的数据域内容
  TMeter645Packet = class
  private
    FAddress: TMeter645Address;
    FControlCode: TMeter645ControlCode;
    FData: AnsiString;
    function GetAppData: AnsiString;
    procedure SetAppData(const Value: AnsiString);
    function GetAsHexString: AnsiString;
    function CalcCs(const PackStr: AnsiString): Byte;
  public
    constructor Create; overload;
    destructor Destroy; override;
    property MeterAddress: TMeter645Address read FAddress;
    property ControlCode: TMeter645ControlCode read FControlCode;
    property AppData: AnsiString read GetAppData write SetAppData;
    property AsHexString: AnsiString read GetAsHexString;
    function GetMessageForDisplay: AnsiString; //直接显示的去掉33变化的报文
    class function GetPack(var HexStr: AnsiString): TMeter645Packet;
    class function MakeMstToRtuPacket(const Address: AnsiString;
       ShrinkDecimals,ControlCode: Byte; const Data: AnsiString): TMeter645Packet;
  end;

implementation

uses uBCDHelper;

{ TMeter645ControlCode }

constructor TMeter645ControlCode.Create;
begin
  inherited;
  FValue := 0;
end;

function TMeter645ControlCode.GetAsHexString: AnsiString;
begin
  Result := chr(FValue);
end;

function TMeter645ControlCode.GetGongNengma: Byte;
begin
  Result := FValue and $1F;
end;

function TMeter645ControlCode.GetHasHouxuzheng: Boolean;
begin
  Result := (FValue and $20)=$20;
end;

function TMeter645ControlCode.GetIsFachu: Boolean;
begin
  Result := not GetIsFanhui;
end;

function TMeter645ControlCode.GetIsFanhui: Boolean;
begin
  Result := (FValue and $80)=$80;
end;

function TMeter645ControlCode.GetIsYichang: Boolean;
begin
  Result := (FValue and $40)=$40;
end;

procedure TMeter645ControlCode.SetAsHexString(const Value: AnsiString);
begin
  if Length(Value)=1 then
    FValue := Ord(Value[1])
  else
    raise Exception.Create('ControlCode is one Byte');
end;

procedure TMeter645ControlCode.SetGongNengma(const Value: Byte);
begin
  FValue := (FValue and $E0)+(Value and $1F); 
end;

procedure TMeter645ControlCode.SetHasHouxuzheng(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $20
  else
    FValue := FValue and $DF;
end;

procedure TMeter645ControlCode.SetIsFachu(const Value: Boolean);
begin
  SetIsFanhui(not Value);
end;

procedure TMeter645ControlCode.SetIsFanhui(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $80
  else
    FValue := FValue and $7F;
end;

procedure TMeter645ControlCode.SetIsYichang(const Value: Boolean);
begin
  if Value then
    FValue := FValue or $40
  else
    FValue := FValue and $BF;
end;

{ TMeter645Address }

class function TMeter645Address.Address2String(
  const BinStr: AnsiString): AnsiString;
begin
  Result := TBcdHelper.BinStr2CStr(ReverseString(BinStr),'');
end;

constructor TMeter645Address.Create;
begin
  inherited;
  FAsHexString := DupeString(#$99,6);
end;

function TMeter645Address.GetAsString: AnsiString;
begin
  Result := Address2String(FAsHexString);
end;

procedure TMeter645Address.SetAsHexString(const Value: AnsiString);
begin
  if Length(Value)=6 then
    FAsHexString := Value
  else
    raise Exception.Create('MeterAddress is 6 Bytes');
end;

procedure TMeter645Address.SetAsString(const Value: AnsiString);
begin
  FAsHexString := String2Address(Value);
end;

procedure TMeter645Address.SetShrinkDecimals(const Value: Byte);
begin
  FShrinkDecimals := Value;
end;

procedure TMeter645Address.SetShrinked(const Value: Boolean);
begin
  FShrinked := Value;
end;

function TMeter645Address.ShrinkAddress(
  const ShrinkDecimals: Byte): AnsiString;
begin
  if ShrinkDecimals>6 then
    Result := FAsHexString
  else
    Result := Copy(FAsHexString,1,6-ShrinkDecimals)+DupeString(#$AA,ShrinkDecimals);
end;

class function TMeter645Address.String2Address(
  const Str: AnsiString): AnsiString;
var
  Temp: AnsiString;
begin
  if Length(Str)<12 then
    Temp := DupeString('0',12-Length(Str))+Str
  else
    Temp := RightStr(Str,12);
  Result := ReverseString(TBcdHelper.CStr2BinStr(Temp));
end;

{ TMeter645Packet }

function TMeter645Packet.CalcCs(const PackStr: AnsiString): Byte;
var
  i: integer;
begin
  Result := 0;
  for i := 1 to Length(PackStr) do
    Result := Result+Ord(PackStr[i]);
end;

constructor TMeter645Packet.Create;
begin
  inherited;
  FAddress := TMeter645Address.Create;
  FControlCode := TMeter645ControlCode.Create;
end;

destructor TMeter645Packet.Destroy;
begin
  FAddress.Free;
  FControlCode.Free;
  inherited;
end;

function TMeter645Packet.GetAppData: AnsiString;
var
  i,len: integer;
begin
  len := Length(FData);
  SetLength(Result,len);
  for i := 1 to len do
    Result[i] := chr(Ord(FData[i])-$33);
end;

function TMeter645Packet.GetAsHexString: AnsiString;
begin
  if not self.FAddress.Shrinked then
    Result := #$68+self.FAddress.AsHexString+#$68+self.FControlCode.AsHexString+
            chr(Length(FData))+FData
  else
    Result := #$68+self.FAddress.ShrinkAddress(self.FAddress.ShrinkDecimals)+#$68+self.FControlCode.AsHexString+
            chr(Length(FData))+FData;
  Result := Result+Chr(CalcCs(Result))+#$16;
end;

function TMeter645Packet.GetMessageForDisplay: AnsiString;
var
  Temp: AnsiString;
begin
  if not self.FAddress.Shrinked then
    Temp := #$68+self.FAddress.AsHexString+#$68+self.FControlCode.AsHexString+
            chr(Length(FData))+self.GetAppData
  else
    Temp := #$68+self.FAddress.ShrinkAddress(self.FAddress.ShrinkDecimals)+#$68+self.FControlCode.AsHexString+
            chr(Length(FData))+self.GetAppData;

  Result := TBcdHelper.BinStr2CStr(temp)+', ??, 16';
end;

class function TMeter645Packet.GetPack(
  var HexStr: AnsiString): TMeter645Packet;
var
  phead: integer;
  len: integer;
function SeekHead(const PackData: AnsiString; const FromPos: integer): integer;
var
  i,len: integer;
begin
  Result := -1;
  len := Length(PackData)-11;
  for i := FromPos to len do
  begin
    if (PackData[i]=#$68) and (PackData[i+7]=#$68) then
    begin
      Result := i;
      break;
    end;
  end;
end;
function GetPackLen(const PackData: AnsiString; const HeadPos: integer): integer;
begin
  Result := Ord(PackData[HeadPos+9]);
end;
function CheckPack(const PackData: AnsiString; const HeadPos,len: integer): Boolean;
var
  cs: Byte;
  i: integer;
begin
  Result := false;
  if (Length(PackData)>=HeadPos+len+11) and (PackData[HeadPos+len+11]=#$16) then
  begin
    cs := 0;
    for i := HeadPos to HeadPos+9+len do
      cs := cs+Ord(PackData[i]);
    if PackData[HeadPos+len+10]=Chr(cs) then
      Result := true;
  end;
end;
begin
  Result := nil;
  phead := SeekHead(HexStr,1);
  while phead<>-1 do
  begin
    len := GetPackLen(HexStr,phead);
    if CheckPack(HexStr,phead,len) then
    begin
      Result := TMeter645Packet.Create;
      Result.MeterAddress.AsHexString := Copy(HexStr,phead+1,6);
      Result.ControlCode.Value := Ord(HexStr[phead+8]);
      Result.FData := Copy(HexStr,phead+10,len);
      HexStr := Copy(HexStr,phead+len+12,Length(HexStr)-phead-len-11);
      break;
    end;
    phead := SeekHead(HexStr,phead+1);
  end;
end;

class function TMeter645Packet.MakeMstToRtuPacket(
  const Address: AnsiString; ShrinkDecimals, ControlCode: Byte;
  const Data: AnsiString): TMeter645Packet;
var
  pack: TMeter645Packet;
begin
  pack := TMeter645Packet.Create;
  pack.MeterAddress.AsString := Address;
  pack.MeterAddress.ShrinkDecimals := ShrinkDecimals;
  pack.MeterAddress.Shrinked := ShrinkDecimals<>0;
  pack.ControlCode.IsYichang := false;
  pack.ControlCode.IsFromZhuzhanToMeter := true;
  pack.ControlCode.HasHouxuzheng := false;
  pack.ControlCode.GongNengma := ControlCode;
  pack.AppData := Data;
  Result := pack;
end;

procedure TMeter645Packet.SetAppData(const Value: AnsiString);
var
  i,len: integer;
begin
  len := Length(Value);
  SetLength(FData,len);
  for i := 1 to len do
    FData[i] := chr(Ord(Value[i])+$33);      //3.  ORD函数是用于取得一个ASCII码的数值,CHR函数正好相反，用于取得一个数值的ASCII值。
end;

end.
