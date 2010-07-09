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
    Label6: TLabel;
    cmbCommBps: TLxchComboBoxStr;
    cmbParity: TLxchComboBox;
    Label1: TLabel;
    edtTimeout: TJvValidateEdit;
    ckbShowAllIoInput: TCheckBox;
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
  cmbParity.Value := Options.Parity;
  edtTimeout.Value := Options.Timeout;
  ckbShowAllIoInput.Checked := Options.ShowAllIoInput;
end;

procedure TSetOptionForm.Button1Click(Sender: TObject);
begin
  Options.CommPort := edtCommPort.Value;
  Options.CommBps := StrToInt(cmbCommBps.Value);
  Options.Parity := cmbParity.Value;
  Options.Timeout := edtTimeout.Value;
  Options.ShowAllIoInput := ckbShowAllIoInput.Checked;

  Options.SaveOptions;
end;

end.
