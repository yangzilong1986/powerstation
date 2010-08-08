unit SetOptionFrm;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls, JvEdit, JvExStdCtrls, JvValidateEdit,
  JvExControls, JvComCtrls, LxchComboBox, LxchComboBoxStr;

type
  TSetOptionForm = class(TForm)
    Panel1: TPanel;
    Button1: TButton;
    Button2: TButton;
    GroupBox2: TGroupBox;
    edtCommPort: TJvValidateEdit;
    Label4: TLabel;
    Label5: TLabel;
    cmbCommBps: TLxchComboBoxStr;
    GroupBox1: TGroupBox;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label6: TLabel;
    Label7: TLabel;
    edtOraIpAddress: TJvIPAddress;
    edtOraPort: TJvValidateEdit;
    edtOraSID: TJvEdit;
    edtOraUser: TJvEdit;
    edtOraPwd: TJvEdit;
    edtSMSC: TJvEdit;
    Label8: TLabel;
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

implementation

uses uoptions;

{$R *.dfm}

procedure TSetOptionForm.FormCreate(Sender: TObject);
begin
  Options.LoadOptions;

  edtCommPort.Value := Options.CommPort;
  cmbCommBps.Value := IntToStr(Options.CommBps);
  edtSMSC.Text := Options.Smsc;
  edtOraIpAddress.Text := Options.OraIPAddress;
  edtOraPort.Value := Options.OraIPPort;
  edtOraSID.Text := Options.OraSID;
  edtOraUser.Text := Options.OraUser;
  edtOraPwd.Text := Options.OraPwd;
end;

procedure TSetOptionForm.Button1Click(Sender: TObject);
begin
  Options.CommPort := edtCommPort.Value;
  Options.CommBps := StrToInt(cmbCommBps.Value);

  Options.Smsc := edtSMSC.Text;
  Options.OraIPAddress := edtOraIpAddress.Text;
  Options.OraIPPort := edtOraPort.Value;
  Options.OraSID := edtOraSID.Text;
  Options.OraUser := edtOraUser.Text;
  Options.OraPwd := edtOraPwd.Text;
  
  Options.SaveOptions;
end;

end.
