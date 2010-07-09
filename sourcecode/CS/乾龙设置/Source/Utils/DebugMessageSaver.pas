unit DebugMessageSaver;

interface

uses
  Classes, Forms, SysUtils, DateUtils;

type
  TDebugMessageSaver = class
  private
    FNeedSave: Boolean;
    FSaveFilesPath: AnsiString;
    FStreamLog: TFileStream;
    FMaxSize: integer;
    procedure DeleteFiles(const Path, ext: AnsiString;
      const days: integer);
    procedure SetSaveFilesPath(const Value: AnsiString);
    function DateTime2Str(const DT: TDateTime): AnsiString;
    procedure SetMaxSize(const Value: integer);
  public
    destructor Destroy; override;
    procedure AfterConstruction; override;
    property SaveFilesPath: AnsiString read FSaveFilesPath write SetSaveFilesPath;
    procedure SaveDebugMessage(const DebugMessage: AnsiString);
    property MaxSize: integer read FMaxSize write SetMaxSize;
  end;

implementation

{ TDebugMessageSaver }

procedure TDebugMessageSaver.AfterConstruction;
begin
  inherited;
  if FMaxSize=0 then FMaxSize := 64;
end;

function TDebugMessageSaver.DateTime2Str(const DT: TDateTime): AnsiString;
var
  AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond :word;
begin
  DecodeDateTime(DT, AYear, AMonth, ADay, AHour, AMinute, ASecond, AMilliSecond);
  Ayear := Ayear mod 100;
  Result := format('%2.2d%2.2d%2.2d%2.2d%2.2d%2.2d',
                     [AYear, AMonth, ADay, AHour, AMinute, ASecond]);
end;

procedure TDebugMessageSaver.DeleteFiles(const Path, ext: AnsiString;
  const days: integer);
var
  sr: TSearchRec;
  FileAttrs: Integer;
begin
  FileAttrs := faAnyFile;
  if FindFirst(Path+'\'+ext, FileAttrs, sr) = 0 then
  begin
    repeat
      if DaysBetween(FileDateToDateTime(sr.Time),Today)>days then
        DeleteFile(Path+'\'+sr.Name);
    until FindNext(sr) <> 0;
    FindClose(sr);
  end;
end;

destructor TDebugMessageSaver.Destroy;
begin
  if Assigned(FStreamLog) then
    FreeAndNil(FStreamLog);
  inherited;
end;

procedure TDebugMessageSaver.SaveDebugMessage(
  const DebugMessage: AnsiString);
var
  Str: AnsiString;
  lsDirName: AnsiString;
  lsFileName: AnsiString;
begin
  if FNeedSave then
  begin
    Str := DateTimeToStr(Now)+':  '+DebugMessage+#13#10;
    try
      if not Assigned(FStreamLog) then
      begin
        if Pos(':',FSaveFilesPath)<>0 then
          lsDirName := FSaveFilesPath
        else
          lsDirName := ExtractFilePath(Application.ExeName)+'\'+FSaveFilesPath;
        if ( not DirectoryExists(lsDirName)) then
        begin
          CreateDir(lsDirName);
        end;
        DeleteFiles(lsDirName,'*.txt',15);
        lsFileName := lsDirName+'\'+DateTime2Str(Now)+'log.txt';
        FStreamLog := TFileStream.Create(lsFileName, fmCreate);
        FStreamlog.Free;
        FStreamlog := nil;
        FStreamLog := TFileStream.Create(lsFileName, fmOpenReadWrite+fmShareDenyNone);
      end;
      {$WARNINGS OFF}
      FStreamLog.WriteBuffer(Pointer(Str)^,Length(Str));
      {$WARNINGS ON}
      if FStreamLog.Size>FMaxSize*1024 then
        FreeAndNil(FStreamLog);
    except
    end;
  end;
end;

procedure TDebugMessageSaver.SetMaxSize(const Value: integer);
begin
  FMaxSize := Value;
end;

procedure TDebugMessageSaver.SetSaveFilesPath(const Value: AnsiString);
begin
  FNeedSave := Trim(Value)<>'';
  FSaveFilesPath := Value;
end;

end.
