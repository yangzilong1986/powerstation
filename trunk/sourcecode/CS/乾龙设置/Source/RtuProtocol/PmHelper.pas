unit PmHelper;

//  CBcdStr: ��ʾ��BCD�ַ�����1�ֽ���ʾΪ2���ַ�
//  BcdStr: �ڲ���BCD�ַ�����1�ֽ�2λ
//  CStr: ��ʾ���ַ�����1�ֽ���ʾΪ2���ַ�
//  BinStr: �ڲ����ַ���

interface

uses
  SysUtils, DateUtils;

type
  TPmData16 = class
  private
    FMinute: Byte;
    FDay: Byte;
    FHour: Byte;
    FSecond: Byte;
    function GetValue: AnsiString;
    procedure SetValue(const Value: AnsiString);
    procedure SetDay(const Value: Byte);
    procedure SetHour(const Value: Byte);
    procedure SetMinute(const Value: Byte);
    procedure SetSecond(const Value: Byte);
  public
    property Value: AnsiString read GetValue write SetValue;
    property Day: Byte read FDay write SetDay;
    property Hour: Byte read FHour write SetHour;
    property Minute: Byte read FMinute write SetMinute;
    property Second: Byte read FSecond write SetSecond;
    procedure SetTime(const dt: TDateTime);
    function CStr: AnsiString;
  end;

implementation

uses uBCDHelper;

{ TPmData16 }

function TPmData16.CStr: AnsiString;
begin
  Result := IntToStr(self.FDay)+'��'+IntToStr(self.FHour)+'ʱ'+
            IntToStr(self.FMinute)+'��'+IntToStr(self.FSecond)+'��';
end;

function TPmData16.GetValue: AnsiString;
begin
  Result := TBcdHelper.Int2BCDString(FSecond,1)+
            TBcdHelper.Int2BCDString(FMinute,1)+
            TBcdHelper.Int2BCDString(FHour,1)+
            TBcdHelper.Int2BCDString(Day,1);
end;

procedure TPmData16.SetDay(const Value: Byte);
begin
  FDay := Value;
end;

procedure TPmData16.SetHour(const Value: Byte);
begin
  FHour := Value;
end;

procedure TPmData16.SetMinute(const Value: Byte);
begin
  FMinute := Value;
end;

procedure TPmData16.SetSecond(const Value: Byte);
begin
  FSecond := Value;
end;

procedure TPmData16.SetTime(const dt: TDateTime);
var
  AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond :word;
begin
  DecodeDateTime(dt, AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond);
  FDay := Aday;
  FHour := AHour;
  FMinute := AMinute;
  FSecond :=ASecond;
end;

procedure TPmData16.SetValue(const Value: AnsiString);
begin
  if Length(Value)=4 then
  begin
    FSecond := TBcdHelper.Bcd2Int(Copy(Value,1,1));
    FMinute := TBcdHelper.Bcd2Int(Copy(Value,2,1));
    FHour := TBcdHelper.Bcd2Int(Copy(Value,3,1));
    FDay := TBcdHelper.Bcd2Int(Copy(Value,4,1));
  end
  else
    raise Exception.Create('Data_16���ݸ�ʽ����Ϊ4�ֽ�');
end;

end.
