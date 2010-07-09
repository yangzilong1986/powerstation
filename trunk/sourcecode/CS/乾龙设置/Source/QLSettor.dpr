program QLSettor;

uses
  Forms,
  MainFrm in 'MainFrm.pas' {MainForm},
  uBCDHelper in 'Utils\uBCDHelper.pas',
  DebugMessageSaver in 'Utils\DebugMessageSaver.pas',
  CmDialogTools in 'Utils\CmDialogTools.pas',
  uOptions in 'Option\uOptions.pas',
  SetOptionFrm in 'Option\SetOptionFrm.pas' {SetOptionForm},
  PmPacket in 'RtuProtocol\PmPacket.pas',
  PmAppPacket in 'RtuProtocol\PmAppPacket.pas',
  PmHelper in 'RtuProtocol\PmHelper.pas',
  uSerialCommunictor in 'Communicator\uSerialCommunictor.pas',
  uMeter645Packet in 'MeterProtocol\uMeter645Packet.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.Title := '现场设置程序';
  Application.CreateForm(TMainForm, MainForm);
  Application.Run;
end.
