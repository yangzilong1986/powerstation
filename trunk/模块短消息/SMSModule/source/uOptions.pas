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
    FSmsc: AnsiString;
    FOraSID: AnsiString;
    FOraIPAddress: AnsiString;
    FOraPwd: AnsiString;
    FOraUser: AnsiString;
    FOraIPPort: integer;
    procedure SetCommBps(const Value: integer);
    procedure SetCommPort(const Value: integer);
    procedure SetSmsc(const Value: AnsiString);
    procedure SetOraIPAddress(const Value: AnsiString);
    procedure SetOraIPPort(const Value: integer);
    procedure SetOraPwd(const Value: AnsiString);
    procedure SetOraSID(const Value: AnsiString);
    procedure SetOraUser(const Value: AnsiString);
  public
    procedure AfterConstruction; override;
    destructor Destroy; override;
    property CommPort: integer read FCommPort write SetCommPort;
    property CommBps: integer read FCommBps write SetCommBps;
    property Smsc: AnsiString read FSmsc write SetSmsc;

    property OraIPAddress: AnsiString read FOraIPAddress write SetOraIPAddress;
    property OraIPPort: integer read FOraIPPort write SetOraIPPort;
    property OraSID: AnsiString read FOraSID write SetOraSID;
    property OraUser: AnsiString read FOraUser write SetOraUser;
    property OraPwd: AnsiString read FOraPwd write SetOraPwd;

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

destructor TOptions.Destroy;
begin
  FIniFile.Free;
  inherited;
end;

procedure TOptions.LoadOptions;
begin
  FCommPort := FIniFile.ReadInteger('System','CommPort',1);
  FCommBps := FIniFile.ReadInteger('System','CommBps',115200);
  FSmsc := FIniFile.ReadString('System','SMSCCode','13700571500');

  FOraIPAddress := FIniFile.ReadString('DbSource','IP','127.0.0.1');
  FOraIPPort := FIniFile.ReadInteger('DbSource','Port',1521);
  FOraSID := FIniFile.ReadString('DbSource','SID','pss');
  FOraUser := FIniFile.ReadString('DbSource','User','pss');
  FOraPwd := FIniFile.ReadString('DbSource','Password','pss');
end;

procedure TOptions.SaveOptions;
begin
  FIniFile.WriteInteger('System','CommPort',FCommPort);
  FIniFile.WriteInteger('System','CommBps',FCommBps);
  FIniFile.WriteString('System','SMSCCode',FSmsc);

  FIniFile.WriteString('DbSource','IP',FOraIPAddress);
  FIniFile.WriteInteger('DbSource','Port',FOraIPPort);
  FIniFile.WriteString('DbSource','SID',FOraSID);
  FIniFile.WriteString('DbSource','User',FOraUser);
  FIniFile.WriteString('DbSource','Password',FOraPwd);
end;

procedure TOptions.SetCommBps(const Value: integer);
begin
  FCommBps := Value;
end;

procedure TOptions.SetCommPort(const Value: integer);
begin
  FCommPort := Value;
end;

procedure TOptions.SetOraIPAddress(const Value: AnsiString);
begin
  FOraIPAddress := Value;
end;

procedure TOptions.SetOraIPPort(const Value: integer);
begin
  FOraIPPort := Value;
end;

procedure TOptions.SetOraPwd(const Value: AnsiString);
begin
  FOraPwd := Value;
end;

procedure TOptions.SetOraSID(const Value: AnsiString);
begin
  FOraSID := Value;
end;

procedure TOptions.SetOraUser(const Value: AnsiString);
begin
  FOraUser := Value;
end;

procedure TOptions.SetSmsc(const Value: AnsiString);
begin
  FSmsc := Value;
end;

initialization
  FOptions := nil;

finalization
  FreeAndNil(FOptions);

end.
