unit PmPacket;

//链路层帧协议封装

interface

uses
  SysUtils,StrUtils;

type
  TPmControlCode = class
  private
    FValue: Byte;
    procedure SetValue(const Value: Byte);
    function GetGongnengma: Byte;
    function GetIsQidongzhen: Boolean;
    function GetIsShangxingzhen: Boolean;
    function GetIsShangxingYaoqiufangwen: Boolean;
    function GetXiaxingZhenjishuwei: Boolean;
    function GetXiaxingZhenjishuYouxiao: Boolean;
    procedure SetGongnengma(const Value: Byte);
    procedure SetIsQidongzhen(const Value: Boolean);
    procedure SetIsShangxingzhen(const Value: Boolean);
    procedure SetShangxingYaoqiufangwen(const Value: Boolean);
    procedure SetXiaxingZhenjishuwei(const Value: Boolean);
    procedure SetXiaxingZhenjishuYouxiao(const Value: Boolean);
  public
    property Value: Byte read FValue write SetValue;
    //下面的都是从value来的
    property IsShangxingzhen: Boolean read GetIsShangxingzhen write SetIsShangxingzhen; //方向
    property IsQidongzhen: Boolean read GetIsQidongzhen write SetIsQidongzhen;  //启动帧
    property XiaxingZhenjishuwei: Boolean read GetXiaxingZhenjishuwei write SetXiaxingZhenjishuwei; //bit
    property XiaxingZhenjishuYouxiao: Boolean read GetXiaxingZhenjishuYouxiao write SetXiaxingZhenjishuYouxiao;
    property IsShangxingYaoqiufangwen: Boolean read GetIsShangxingYaoqiufangwen write SetShangxingYaoqiufangwen;
    property Gongnengma: Byte read GetGongnengma write SetGongnengma;
    function CStr: AnsiString;
  end;

  TPmAddress = class
  private
    FXingzhengquhao: AnsiString;
    Fzhongduandizhi: AnsiString;
    FZudizhiBiaozhi: Boolean;
    FZhuzhandizhi: byte;
    procedure SetXingzhengquhao(const Value: AnsiString);
    procedure Setzhongduandizhi(const Value: AnsiString);
    procedure SetZhuzhandizhi(const Value: byte);
    procedure SetZudizhiBiaozhi(const Value: Boolean);
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
    function GetZhongduanDizhiL: AnsiString;
    procedure SetZhongduanDizhiL(const Value: AnsiString);
  public
    procedure AfterConstruction; override;
    property Xingzhengquhao: AnsiString read FXingzhengquhao write SetXingzhengquhao;
    property zhongduandizhi: AnsiString read Fzhongduandizhi write Setzhongduandizhi;
    property Zhuzhandizhi: byte read FZhuzhandizhi write SetZhuzhandizhi;
    property ZudizhiBiaozhi: Boolean read FZudizhiBiaozhi write SetZudizhiBiaozhi;
    property Value: AnsiString read GetValue write SetValue;
    property ZhongduanDizhiL: AnsiString read GetZhongduanDizhiL write SetZhongduanDizhiL; //地区码+终端地址（CBIN）
    function CStr: AnsiString;
  end;

  TPmLinkLayerPacket = class
  private
    FControlCode: TPmControlCode;
    FAddress: TPmAddress;
    FContainLen: Word;
    FData: AnsiString;
    procedure SetData(const Value: AnsiString);
    function GetAsBinStr: AnsiString;
    procedure SetAsBinStr(const Value: AnsiString);
    class function CalcSum(p: PAnsiChar; const len: word): AnsiChar;
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    property ContainLen: Word read FContainLen; //链路用户数据长度
    property ControlCode: TPmControlCode read FControlcode;  //链路控制字
    property Address: TPmAddress read FAddress;  //链路地址域
    property Data: AnsiString read FData write SetData; //链路用户数据
    class function GetPacket(var DataStr: AnsiString): TPmLinkLayerPacket; virtual;
    property AsBinStr: AnsiString read GetAsBinStr write SetAsBinStr;
  end;

  
implementation

uses PmHelper, uBCDHelper;

const
  VERSION_CODE = $02;

{ TPmLinkLayerPacket }

procedure TPmLinkLayerPacket.AfterConstruction;
begin
  inherited;
  FControlCode := TPmControlCode.Create;
  FAddress := TPmAddress.Create;
end;

class function TPmLinkLayerPacket.CalcSum(p: PAnsiChar;
  const len: word): AnsiChar;
var
  b: Byte;
  i: Word;
begin
  b := 0;
  for i := 0 to len-1 do
    b := b+Ord((p+i)^);
  Result := Chr(b);
end;

destructor TPmLinkLayerPacket.Destroy;
begin
  FControlCode.Free;
  FAddress.Free;
end;

function TPmLinkLayerPacket.GetAsBinStr: AnsiString;
var
  Temp: AnsiString;
begin
  temp := chr(FControlCode.Value)+FAddress.Value+FData;
  Result := #$68+TBcdHelper.Word2BinStr(((FContainLen+6) shl 2)+VERSION_CODE)+
            TBcdHelper.Word2BinStr(((FContainLen+6) shl 2)+VERSION_CODE)+#$68+
            temp+CalcSum(@(temp[1]),Length(temp));
  Result := Result+#$16;
end;

class function TPmLinkLayerPacket.GetPacket(var DataStr: AnsiString): TPmLinkLayerPacket;
var
  pb,de: PAnsiChar;
  len: Word;
  i: integer;
  function PickHead(var pb: PAnsiChar; const de: PAnsiChar): Boolean;
  begin
    while (pb+11<=de) and ((pb^<>#$68) or ((pb+5)^<>#$68)) do
      pb := pb+1;
    Result := (pb+11<=de) and (pb^=#$68) and ((pb+5)^=#$68);
  end;
begin
  Result := nil;
  if Length(DataStr)<12 then exit;

  pb := @(DataStr[1]);
  de := pb+Length(DataStr);
  while PickHead(pb,de) do
  begin
    i := pb-@(DataStr[1])+1;
    len := TBcdHelper.BinStr2Word(Copy(DataStr,i+1,2));
    if (len<>(TBcdHelper.BinStr2Word(Copy(DataStr,i+3,2)))) or
      ((len and $03)<>VERSION_CODE) then
    begin
      pb := pb+1;
      continue;
    end;
    len := len shr 2;

    if i+len+7>Length(DataStr) then
    begin
      pb := pb+1;
      continue;
    end;
    if (pb+len+7)^<>#$16 then
    begin
      pb := pb+1;
      continue;
    end;
    if CalcSum(pb+6,len)<>(pb+len+6)^ then
    begin
      pb := pb+1;
      continue;
    end;
    Result := TPmLinkLayerPacket.Create;
    Result.FContainLen := len-6;
    Result.FControlCode.FValue := Ord((pb+6)^);
    Result.FAddress.Value := Copy(DataStr,i+7,5);
    Result.FData := Copy(DataStr,i+12,len-6);
    DataStr := Copy(DataStr,i+8+len,Length(DataStr)-i-7-len);
    break;
  end;
end;

procedure TPmLinkLayerPacket.SetAsBinStr(const Value: AnsiString);
var
  pb: PAnsiChar;
  len: Word;
  i: integer;
begin
  if Length(Value)<12 then exit;

  pb := @(Value[1]);

  i := pb-@(Value[1])+1;
  len := TBcdHelper.BinStr2Word(Copy(Value,i+1,2));
  if (len<>(TBcdHelper.BinStr2Word(Copy(Value,i+3,2)))) or
    ((len and $03)<>VERSION_CODE) then
  begin
    raise Exception.Create('报文结构非法: 长度域');
  end;
  len := len shr 2;

  if i+len+7>Length(Value) then
    raise Exception.Create('报文结构非法: 报文太短');
  if (pb+len+7)^<>#$16 then
    raise Exception.Create('报文结构非法: 结束标志');
  if CalcSum(pb+6,len)<>(pb+len+6)^ then
  begin
    raise Exception.Create('报文结构非法: 校验位');
  end;
  self.FContainLen := len-6;
  self.FControlCode.FValue := Ord((pb+6)^);
  self.FAddress.Value := Copy(Value,i+7,5);
  self.FData := Copy(Value,i+12,len-6);
end;

procedure TPmLinkLayerPacket.SetData(const Value: AnsiString);
begin
  FData := Value;
  self.FContainLen := Length(Value);
end;

{ TPmControlCode }

function TPmControlCode.GetGongnengma: Byte;
begin
  Result := self.FValue and $0F;
end;

function TPmControlCode.GetIsQidongzhen: Boolean;
begin
  Result := (self.FValue and $40)=$40;
end;

function TPmControlCode.GetIsShangxingzhen: Boolean;
begin
  Result := (self.FValue and $80)=$80;
end;

function TPmControlCode.GetIsShangxingYaoqiufangwen: Boolean;
begin
  Result := (self.FValue and $20)=$20;
end;

function TPmControlCode.GetXiaxingZhenjishuwei: Boolean;
begin
  Result := (self.FValue and $20)=$20;
end;

function TPmControlCode.GetXiaxingZhenjishuYouxiao: Boolean;
begin
  Result := (self.FValue and $10)=$10;
end;

procedure TPmControlCode.SetGongnengma(const Value: Byte);
begin
  self.FValue := (self.FValue and $F0) or (Value and $0F);
end;

procedure TPmControlCode.SetIsQidongzhen(const Value: Boolean);
begin
  if Value then
    self.FValue := self.FValue or $40
  else
    self.FValue := self.FValue and $BF;
end;

procedure TPmControlCode.SetIsShangxingzhen(const Value: Boolean);
begin
  if Value then
    self.FValue := self.FValue or $80
  else
    self.FValue := self.FValue and $7F;
end;

procedure TPmControlCode.SetValue(const Value: Byte);
begin
  FValue := Value;
end;

procedure TPmControlCode.SetShangxingYaoqiufangwen(const Value: Boolean);
begin
  if Value then
    self.FValue := self.FValue or $20
  else
    self.FValue := self.FValue and $DF;
end;

procedure TPmControlCode.SetXiaxingZhenjishuwei(const Value: Boolean);
begin
  if Value then
    self.FValue := self.FValue or $20
  else
    self.FValue := self.FValue and $DF;
end;

procedure TPmControlCode.SetXiaxingZhenjishuYouxiao(const Value: Boolean);
begin
  if Value then
    self.FValue := self.FValue or $10
  else
    self.FValue := self.FValue and $EF;
end;

function TPmControlCode.CStr: AnsiString;
begin
  if self.IsShangxingzhen then
    Result := '上行帧; '
  else
    Result := '下行帧; ';
  if self.IsQidongzhen then
    Result := Result+'启动帧; '
  else
    Result := Result+'非启动帧; ';
  if self.IsShangxingzhen then
  begin
    if self.IsShangxingYaoqiufangwen then
      Result := Result+'要求访问; '
    else
      Result := Result+'不要求访问; ';
  end
  else
  begin
    if self.XiaxingZhenjishuYouxiao then
    begin
      if self.XiaxingZhenjishuwei then
        Result := Result+'帧计数有效; 帧计数位=1'
      else
        Result := Result+'帧计数有效; 帧计数位=0';
    end
    else
      Result := Result+'帧计数无效; ';
  end;
  Result := Result+'功能码='+IntToStr(self.Gongnengma);
end;

{ TPmAddress }

procedure TPmAddress.AfterConstruction;
begin
  inherited;
  self.FXingzhengquhao := '0000';
  self.Fzhongduandizhi := '0001';
  self.FZhuzhandizhi := 1;
  self.FZudizhiBiaozhi := false;
end;

function TPmAddress.CStr: AnsiString;
begin
  Result := '终端地址='+self.ZhongduanDizhiL+'; 主站地址='+IntToStr(self.Zhuzhandizhi);
  if self.ZudizhiBiaozhi then
    Result := Result+'; 组播'
  else
    Result := Result+'; 单播';
end;

function TPmAddress.GetValue: AnsiString;
begin
  if self.FZudizhiBiaozhi then
    Result := ReverseString(TBcdHelper.CStr2BinStr(FXingzhengquhao))+
              ReverseString(TBcdHelper.CStr2BinStr(Fzhongduandizhi))+
              chr((Zhuzhandizhi shl 1)+1)
  else
    Result := ReverseString(TBcdHelper.CStr2BinStr(FXingzhengquhao))+
              ReverseString(TBcdHelper.CStr2BinStr(Fzhongduandizhi))+
              chr((Zhuzhandizhi shl 1));
end;

function TPmAddress.GetZhongduanDizhiL: AnsiString;
begin
  Result := FXingzhengquhao+Fzhongduandizhi;
end;

procedure TPmAddress.SetValue(const Value: AnsiString);
var
  t: Byte;
begin
  if Length(Value)=5 then
  begin
    self.FXingzhengquhao := TBcdHelper.BinStr2CStr(ReverseString(Copy(Value,1,2)),'');
    self.Fzhongduandizhi := TBcdHelper.BinStr2CStr(ReverseString(Copy(Value,3,2)),'');
    t := Ord(Value[5]);
    self.FZhuzhandizhi := t shr 1;
    self.FZudizhiBiaozhi := (t and 1)=1;
  end;
end;

procedure TPmAddress.SetXingzhengquhao(const Value: AnsiString);
begin
  if Length(Value)=4 then
    FXingzhengquhao := Value
  else
    raise Exception.Create('行政区划码必须是4位');
end;

procedure TPmAddress.Setzhongduandizhi(const Value: AnsiString);
begin
  if Length(Value)=4 then
    Fzhongduandizhi := Value
  else
    raise Exception.Create('终端地址必须是4位');
end;

procedure TPmAddress.SetZhongduanDizhiL(const Value: AnsiString);
begin
  if Length(Value)=8 then
  begin
    FXingzhengquhao := Copy(Value,1,4);
    Fzhongduandizhi := Copy(Value,5,4);
  end
  else
    raise Exception.Create('终端全地址必须是8位');
end;

procedure TPmAddress.SetZhuzhandizhi(const Value: byte);
begin
  if Value<128 then
    FZhuzhandizhi := Value
  else
    raise Exception.Create('主站地址不能大于127');
end;

procedure TPmAddress.SetZudizhiBiaozhi(const Value: Boolean);
begin
  FZudizhiBiaozhi := Value;
end;

end.
