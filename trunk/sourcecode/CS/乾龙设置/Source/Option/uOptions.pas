unit uoptions;

interface

uses
  SysUtils, IniFiles;

type
  TOptions = class
  private
    FIniFile: TIniFile;
    FCommPort: integer;
    FCommBps: integer;
    FParity: Byte;
    FTimeout: Longword;
    FShowAllIoInput: Boolean;
    procedure SetCommBps(const Value: integer);
    procedure SetCommPort(const Value: integer);
    procedure SetParity(const Value: Byte);
    procedure SetTimeout(const Value: Longword);
    procedure SetShowAllIoInput(const Value: Boolean);
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    property CommPort: integer read FCommPort write SetCommPort;
    property CommBps: integer read FCommBps write SetCommBps;
    property Parity: Byte read FParity write SetParity;
    property Timeout: Longword read FTimeout write SetTimeout;
    property ShowAllIoInput: Boolean read FShowAllIoInput write SetShowAllIoInput;

    function CParity: AnsiString;
    procedure LoadOptions;
    procedure SaveOptions;
  end;

  function Options: TOptions;

implementation

var
  FOptions: TOptions;

function Options: TOptions;
begin
  if FOptions=nil then
  begin
    FOptions := TOptions.Create;
    FOptions.LoadOptions;
  end;
  Result := FOptions;
end;

{ TOptions }

procedure TOptions.AfterConstruction;
begin
  inherited;
  FIniFile := TIniFile.Create('.\Options.ini');
end;

function TOptions.CParity: AnsiString;
begin
  case FParity of
    0: Result := '无校验';
    1: Result := '奇校验';
    2: Result := '偶校验';
    3: Result := '标记(恒1)';
    4: Result := '空(恒0)';
  else
    Result := '非法';
  end;
end;

destructor TOptions.Destroy;
begin
  FIniFile.Free;
  inherited;
end;

procedure TOptions.LoadOptions;
begin
  FCommPort := FIniFile.ReadInteger('System','CommPort',1);
  FCommBps := FIniFile.ReadInteger('System','CommBps',9600);
  FParity := FIniFile.ReadInteger('System','CommParity',2);
  FTimeout := FIniFile.ReadInteger('System','Timeout',1000);
  FShowAllIoInput := FIniFile.ReadBool('System','ShowAllIoInput',false);
end;

procedure TOptions.SaveOptions;
begin
  FIniFile.WriteInteger('System','CommPort',FCommPort);
  FIniFile.WriteInteger('System','CommBps',FCommBps);
  FIniFile.WriteInteger('System','CommParity',FParity);
  FIniFile.WriteInteger('System','Timeout',FTimeout);
  FIniFile.WriteBool('System','ShowAllIoInput',FShowAllIoInput);
end;

procedure TOptions.SetCommBps(const Value: integer);
begin
  FCommBps := Value;
end;

procedure TOptions.SetCommPort(const Value: integer);
begin
  FCommPort := Value;
end;

procedure TOptions.SetParity(const Value: Byte);
begin
  FParity := Value;
end;

procedure TOptions.SetShowAllIoInput(const Value: Boolean);
begin
  FShowAllIoInput := Value;
end;

procedure TOptions.SetTimeout(const Value: Longword);
begin
  FTimeout := Value;
end;

initialization
  FOptions := nil;

finalization
  FreeAndNil(FOptions);

end.
