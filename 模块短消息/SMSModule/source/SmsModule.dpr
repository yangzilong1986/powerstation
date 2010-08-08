program SmsModule;

uses
  Forms,
  MainFrm in 'MainFrm.pas' {MainForm},
  uoptions in 'uOptions.pas',
  SetOptionFrm in 'SetOptionFrm.pas' {SetOptionForm},
  uBCDHelper in 'uBCDHelper.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TMainForm, MainForm);
  Application.Run;
end.
