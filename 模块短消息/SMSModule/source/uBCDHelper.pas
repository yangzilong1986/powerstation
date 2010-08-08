unit uBCDHelper;

interface

uses
  SysUtils, Variants, DateUtils, Types, StrUtils;
  
type
  TBcdHelper = class
  private
    class function ValidCBcdStr(const CBcdStr: AnsiString): Boolean;
    class function IsDigitalChar(const ch: AnsiChar): Boolean;
    class function IsHexChar(const ch: AnsiChar): Boolean;
    class function ValidBcdStr(const BCDStr: AnsiString): Boolean;
    class function BcdChar2Int(const BCDChar: AnsiChar): Integer;
  public
    class function Bcd2Dec(const bcd: Byte): Byte;
    class function Dec2BCD(const DecByte: Byte): Byte;
    class function BinStr2CStr(const BinStr: AnsiString; const DecStr: AnsiString=', '): AnsiString;
    class function CStr2BinStr(const CStr: AnsiString): AnsiString;
    class function CBcdStr2BinStr(const CBcdStr: AnsiString): AnsiString;
    class function BinStr2Word(const BinStr: AnsiString): Word;
    class function BinStr2DWord(const BinStr: AnsiString): DWord;
    class function BinStr2ThreeByte(const BinStr: AnsiString): DWord;
    class function BinStr2ASCStr(const BinStr: AnsiString): AnsiString;
    class function Dword2BinStr(const dw: DWord): AnsiString;
    class function Word2BinStr(const dw: Word): AnsiString;
    class function ThreeByte2BinStr(const dw: DWord): AnsiString;
    class function Bcd2Int(const BinStr: AnsiString): Integer;
    class function Bcd2SignedInt(const BinStr: AnsiString): Integer;
    class function Bcd2Float(const BinStr: AnsiString; const Decs: integer): Double;
    class function Bcd2SignedFloat(const BinStr: AnsiString; const Decs: integer): Double;
    class function Bcd2IntVariant(const BinStr: AnsiString): Variant;
    class function Bcd2SignedIntVariant(const BinStr: AnsiString): Variant;
    class function Bcd2FloatVariant(const BinStr: AnsiString; const Decs: integer): Variant;
    class function Bcd2SignedFloatVariant(const BinStr: AnsiString; const Decs: integer): Variant;
    class function BCD2DateTimeVariant(const bcdstr: AnsiString;
                                       const DateTimeFormat: AnsiString): Variant;
    class function DateTime2BCDString(const dt: TDateTime;
                                      const DateTimeFormat: AnsiString): AnsiString;
    class function BCD2DateTimeCString(const bcdstr: AnsiString;
                                       const DateTimeFormat: AnsiString): Variant;
    class function Int2BCDString(const val: Int64; const Bytes: Byte): AnsiString;
    class function SignedInt2BCDString(const val: Int64; const Bytes: Byte): AnsiString;
    class function FixedAsciiStr(const ascii: AnsiString; len: integer): AnsiString;
    class function fixedAsciiStrToStr(const ascii: AnsiString): AnsiString;
  end;

implementation

{ TBcdHelper }

const
  HexCharArray: array [0..15] of AnsiChar = ('0','1','2','3','4','5','6','7',
                                             '8','9','A','B','C','D','E','F');

class function TBcdHelper.BCD2DateTimeCString(const bcdstr,
  DateTimeFormat: AnsiString): Variant;
var
  AYear, AMonth, ADay, AHour, AMinute, ASecond,WeekDay: integer;
  p: integer;
  ADateTimeFormat: AnsiString;
  temp: AnsiString;
begin
  if ValidBcdStr(bcdstr) then
  begin
    if length(bcdstr)<>length(DateTimeFormat) div 2 then
    begin
      Result := Null;
      exit;
    end;

    ADateTimeFormat := UpperCase(DateTimeFormat);

    AYear := -1;
    AMonth := -1;
    ADay := -1;
    AHour := -1;
    AMinute := -1;
    ASecond := -1;
    WeekDay := -1;

    p := pos('YYYY',ADateTimeFormat);
    if p<>0 then
      AYear := BCD2Int(copy(bcdstr,(p+1) div 2,2))
    else
    begin
      p := pos('YY',ADateTimeFormat);
      if p<>0 then
        AYear := 2000+BCD2Int(copy(bcdstr,(p+1) div 2,1));
    end;

    p := pos('MM',ADateTimeFormat);
    if p<>0 then
      AMonth := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('DD',ADateTimeFormat);
    if p<>0 then
      ADay := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('HH',ADateTimeFormat);
    if p<>0 then
      AHour := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('MI',ADateTimeFormat);
    if p<>0 then
      AMinute := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('SS',ADateTimeFormat);
    if p<>0 then
      ASecond := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('WW',ADateTimeFormat);
    if p<>0 then
      WeekDay := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    temp := '';
    if AYear<>-1 then
      temp := temp+IntToStr(AYear)+'年';
    if AMonth<>-1 then
      temp := temp+IntToStr(AMonth)+'月';
    if ADay<>-1 then
      temp := temp+IntToStr(ADay)+'日';
    if AHour<>-1 then
      temp := temp+IntToStr(AHour)+'时';
    if AMinute<>-1 then
      temp := temp+IntToStr(AMinute)+'分';
    if ASecond<>-1 then
      temp := temp+IntToStr(ASecond)+'秒';
    if WeekDay<>-1 then
      temp := temp+'周'+IntToStr(WeekDay);
    Result := temp;
  end
  else
    Result := Null;
end;

class function TBcdHelper.BCD2DateTimeVariant(const bcdstr,
  DateTimeFormat: AnsiString): Variant;
var
  AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond: word;
  p: integer;
  ADateTimeFormat: AnsiString;
  ANow: TDateTime;
  ADateTime: TDateTime;
begin
  if ValidBcdStr(bcdstr) then
  begin
    if length(bcdstr)<>length(DateTimeFormat) div 2 then
    begin
      Result := Null;
      exit;
    end;

    ANow := Now;
    ADateTimeFormat := UpperCase(DateTimeFormat);
    DecodeDateTime(ANow, AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond);
    AHour := 0;
    AMinute := 0;
    ASecond := 0;
    AMilliSecond := 0;

    p := pos('YYYY',ADateTimeFormat);
    if p<>0 then
      AYear := BCD2Int(copy(bcdstr,(p+1) div 2,2))
    else
    begin
      p := pos('YY',ADateTimeFormat);
      if p<>0 then
        AYear := 2000+BCD2Int(copy(bcdstr,(p+1) div 2,1));
    end;

    p := pos('MM',ADateTimeFormat);
    if p<>0 then
      AMonth := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('MW',ADateTimeFormat);
    if p<>0 then
    begin
      AMonth := Bcd2Dec(Ord(bcdstr[(p+1) div 2]) and $1F);
    end;

    p := pos('DD',ADateTimeFormat);
    if p<>0 then
      ADay := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('HH',ADateTimeFormat);
    if p<>0 then
      AHour := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('MI',ADateTimeFormat);
    if p<>0 then
      AMinute := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    p := pos('SS',ADateTimeFormat);
    if p<>0 then
      ASecond := BCD2Int(copy(bcdstr,(p+1) div 2,1));

    try
      ADateTime := encodeDateTime(AYear, AMonth, ADay, AHour, AMinute, ASecond,AMilliSecond);
      result := ADateTime;
    except
      Result := Null;
    end;
  end
  else
    Result := Null;
end;

class function TBcdHelper.Bcd2Dec(const bcd: Byte): Byte;
begin
  Result := (bcd and $0F)+ ((bcd shr 4) and $0F)*10;
end;

class function TBcdHelper.Bcd2Float(const BinStr: AnsiString;
  const Decs: integer): Double;
var
  i: integer;
begin
  Result := Bcd2Int(BinStr);
  for i := 1 to decs do
    Result := Result/10.0;
end;

class function TBcdHelper.Bcd2FloatVariant(const BinStr: AnsiString;
  const Decs: integer): Variant;
begin
  if ValidBcdStr(BinStr) then
    Result := Bcd2Float(BinStr,Decs)
  else
    Result := Null;
end;

class function TBcdHelper.Bcd2Int(const BinStr: AnsiString): Integer;
var
  i: integer;
begin
  if ValidBcdStr(BinStr) then
  begin
    Result := 0;
    for i := Length(BinStr) downto 1 do
      Result := Result*100+BcdChar2Int(BinStr[i]);
  end
  else
    Result := 0;
end;

class function TBcdHelper.Bcd2IntVariant(
  const BinStr: AnsiString): Variant;
begin
  if ValidBcdStr(BinStr) then
     Result := Bcd2Int(BinStr)
  else
    Result := Null;
end;

class function TBcdHelper.Bcd2SignedFloat(const BinStr: AnsiString;
  const Decs: integer): Double;
var
  i: integer;
begin
  Result := Bcd2SignedInt(BinStr);
  for i := 1 to decs do
    Result := Result/10.0;
end;

class function TBcdHelper.Bcd2SignedFloatVariant(const BinStr: AnsiString;
  const Decs: integer): Variant;
begin
  if ValidBcdStr(BinStr) then
    Result := Bcd2SignedFloat(BinStr,Decs)
  else
    Result := Null;
end;

class function TBcdHelper.Bcd2SignedInt(const BinStr: AnsiString): Integer;
var
  isFushu: Boolean;
  temp: AnsiString;
  b: Byte;
  len: integer;
begin
  len := length(BinStr);
  if len<>0 then
  begin
    b := Ord(BinStr[len]);
    isFushu := (b and $80)=$80;
    temp := BinStr;
    temp[len] := chr(b and $7F);
    if isFushu then
    begin
     // temp[len] := chr(b and $7F);
      Result := -1*Bcd2Int(temp);  //??
    end
    else
      Result := Bcd2Int(temp);  //??
  end
  else
    Result := 0;
end;

class function TBcdHelper.Bcd2SignedIntVariant(
  const BinStr: AnsiString): Variant;
begin
  if ValidBcdStr(BinStr) then
     Result := Bcd2SignedInt(BinStr)
  else
    Result := Null;
end;

class function TBcdHelper.BcdChar2Int(const BCDChar: AnsiChar): Integer;
var
  b: Byte;
begin
  b := Ord(BCDChar);
  Result := (b and $0F)+((b and $F0) shr 4)*10;
end;

class function TBcdHelper.BinStr2ASCStr(
  const BinStr: AnsiString): AnsiString;
var
  i: integer;
begin
  Result := ReverseString(BinStr);
  i := 1;
  while i<= Length(BinStr) do
  begin
    if BinStr[i]=#0 then
      break;
    Inc(i);
  end;
  SetLength(Result,i);
end;

class function TBcdHelper.BinStr2CStr(const BinStr,
  DecStr: AnsiString): AnsiString;
var
  slen,tlen,jlen: integer;
  i,j: integer;
  ch: Byte;
  procedure CopyDecStr(p: PAnsiChar);
  var
    i: integer;
  begin
    for i := 1 to jlen do
    begin
      (p+i)^ := DecStr[i];
    end;
  end;
begin
  jlen := Length(DecStr);
  slen := Length(BinStr);
  if slen<>0 then
    tlen := slen*2+jlen*(slen-1)
  else
    tlen := 0;

  SetLength(Result,tlen);
  j := 1;
  for i := 1 to slen do
  begin
    ch := Byte(BinStr[i]);
    Result[j] := HexCharArray[ch shr 4];
    Result[j+1] := HexCharArray[ch and $0F];
    if i<>slen then
      CopyDecStr(@(Result[j+1]));
    inc(j,2+jlen);
  end;
end;

class function TBcdHelper.BinStr2DWord(const BinStr: AnsiString): DWord;
begin
  if Length(BinStr)=4 then
  begin
    Result := Ord(BinStr[1])+Ord(BinStr[2])*$100+
              Ord(BinStr[3])*$10000+Ord(BinStr[4])*$1000000;
  end
  else
    Result := 0;
end;

class function TBcdHelper.BinStr2ThreeByte(
  const BinStr: AnsiString): DWord;
begin
  if Length(BinStr)=3 then
  begin
    Result := Ord(BinStr[1])+Ord(BinStr[2])*$100+
              Ord(BinStr[3])*$10000;
  end
  else
    Result := 0;
end;

class function TBcdHelper.BinStr2Word(const BinStr: AnsiString): Word;
begin
  if Length(BinStr)=2 then
  begin
    Result := Ord(BinStr[1])+Ord(BinStr[2])*$100;
  end
  else
    Result := 0;
end;

class function TBcdHelper.CBcdStr2BinStr(
  const CBcdStr: AnsiString): AnsiString;
begin
  if ValidCBcdStr(CBcdStr) then
    Result := CStr2BinStr(CBcdStr)
  else
    raise Exception.Create('不是BCD字符串');
end;

class function TBcdHelper.CStr2BinStr(const CStr: AnsiString): AnsiString;
var
  p,ep: PAnsiChar;
  slen,tlen: integer;
  ch: AnsiChar;
  function toHex(ch: AnsiChar): Byte;
  begin
    if ((ch>='0') and (ch<='9')) then
      Result := Ord(ch)-Ord('0')
    else if ((ch>='A') and (ch<='F')) then
      Result := Ord(ch)-Ord('A')+10
    else
      Result := Ord(ch)-Ord('a')+10;
  end;
  function PickATwoChar(var p: PAnsiChar; const ep: PAnsiChar; var ch: AnsiChar): Boolean;
  var
    b: Byte;
  begin
    while ((p<ep) and (not(IsHexChar(p^)))) do p := p+1;
    if (p=ep) then
      Result := false
    else
    begin
      Result := true;
      b := toHex(p^);
      p := p+1;
      if IsHexChar(p^) then
      begin
        b := (b shl 4)+toHex(p^);
        p := p+1;
      end;
      ch := chr(b);
    end;
  end;
begin
  slen := Length(CStr);
  tlen := 0;
  SetLength(Result,slen);
  p := @(CStr[1]);
  ep := p+Length(CStr);
  while PickATwoChar(p,ep,ch) do
  begin
    tlen := tlen+1;
    Result[tlen] := ch;
  end;
  SetLength(Result,tlen);
end;

class function TBcdHelper.DateTime2BCDString(const dt: TDateTime;
  const DateTimeFormat: AnsiString): AnsiString;
var
  AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond, AWeek: word;
  p: integer;
  ADateTimeFormat: AnsiString;
begin
  ADateTimeFormat := UpperCase(DateTimeFormat);
  DecodeDateTime(dt, AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond);
  AWeek := DayOfWeek(dt)-1; //星期天=0
  
  p := 1;
  Result := '';
  while p<=Length(ADateTimeFormat) do
  begin
    if Copy(ADateTimeFormat,p,4)='YYYY' then
    begin
      Result := Result+Int2BCDString(AYear,2);
      p := p+4;
    end
    else if Copy(ADateTimeFormat,p,2)='YY' then
    begin
      Result := Result+Int2BCDString(AYear mod 100,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='MM' then
    begin
      Result := Result+Int2BCDString(AMonth,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='DD' then
    begin
      Result := Result+Int2BCDString(ADay,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='HH' then
    begin
      Result := Result+Int2BCDString(AHour,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='MI' then
    begin
      Result := Result+Int2BCDString(AMinute,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='SS' then
    begin
      Result := Result+Int2BCDString(ASecond,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='WW' then
    begin
      Result := Result+Int2BCDString(AWeek,1);
      p := p+2;
    end
    else if Copy(ADateTimeFormat,p,2)='MW' then
    begin
      Result := Result+Int2BCDString(AMonth,1);
      p := p+2;
      Result[Length(Result)] := AnsiChar((Ord(Result[Length(Result)]) and $1F) +DayOfTheWeek(dt)*32);
    end;
  end;
end;

class function TBcdHelper.Dec2BCD(const DecByte: Byte): Byte;
begin
  result := ((decByte div 10) shl 4) or (decByte mod 10);
end;

class function TBcdHelper.Dword2BinStr(const dw: DWord): AnsiString;
var
  i: integer;
  w: DWord;
begin
  Result := '';
  w := dw;
  for i := 0 to 3 do
  begin
    Result := Result+Chr(w mod $100);
    w := w div $100;
  end;
end;

class function TBcdHelper.FixedAsciiStr(
  const ascii: AnsiString; len: integer): AnsiString;
begin
  if Length(ascii)>=len then
    Result := Copy(ascii,1,len)
  else
    Result := ascii+DupeString(#0,len-Length(ascii));
end;

class function TBcdHelper.fixedAsciiStrToStr(
  const ascii: AnsiString): AnsiString;
begin
  Result := Copy(ascii,1,StrLen(PChar(ascii)));
end;

class function TBcdHelper.Int2BCDString(const val: Int64;
  const Bytes: Byte): AnsiString;
var
  i: integer;
  AMod,ADiv: int64;
  s:AnsiString;
begin
  s := '';
  ADiv := val;
  for i:=1 to Bytes do
  begin
    AMod := ADiv mod 100;
    ADiv := ADiv div 100;
    s := s+chr(DEC2BCD(AMod));
  end;
  Result := s;
end;

class function TBcdHelper.IsDigitalChar(const ch: AnsiChar): Boolean;
begin
  Result := (ch>='0') and (ch<='9');
end;

class function TBcdHelper.IsHexChar(const ch: AnsiChar): Boolean;
function isHA(const ch: AnsiChar): Boolean;
begin
  Result := ((ch>='a') and (ch<='f')) or ((ch>='A') and (ch<='F'));
end;
begin
  Result := IsDigitalChar(ch) or isHA(ch);
end;

class function TBcdHelper.SignedInt2BCDString(const val: Int64;
  const Bytes: Byte): AnsiString;
begin  //不做范围检查
  Result := Int2BCDString(abs(val),Bytes);
  if val<0 then
    Result[Bytes] := AnsiChar(Ord(Result[Bytes]) or $80)
  else
    Result[Bytes] := AnsiChar(Ord(Result[Bytes]) and $7F);
end;

class function TBcdHelper.ThreeByte2BinStr(const dw: DWord): AnsiString;
var
  i: integer;
  w: DWord;
begin
  Result := '';
  w := dw;
  for i := 0 to 2 do
  begin
    Result := Result+Chr(w mod $100);
    w := w div $100;
  end;
end;

class function TBcdHelper.ValidBcdStr(const BCDStr: AnsiString): Boolean;
var
  i: integer;
  b: Byte;
begin
  Result := true;
  for i := 1 to Length(BCDStr) do
  begin
    b := Ord(BCDStr[i]);
    if ((b and $0F)>9) or ((b and $F0)>$90) then
    begin
      Result := false;
      break;
    end;
  end;
end;

class function TBcdHelper.ValidCBcdStr(const CBcdStr: AnsiString): Boolean;
var
  i: integer;
begin
  Result := true;
  for i := 1 to Length(CBcdStr) do
  begin
    if not IsDigitalChar(CBcdStr[i]) then
    begin
      Result := false;
      break;
    end;
  end;
end;

class function TBcdHelper.Word2BinStr(const dw: Word): AnsiString;
var
  i: integer;
  w: Word;
begin
  Result := '';
  w := dw;
  for i := 0 to 1 do
  begin
    Result := Result+Chr(w mod $100);
    w := w div $100;
  end;
end;

end.
