unit MainFrm;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, StdActns, ActnList, StdCtrls, ComCtrls, ExtCtrls,
  DebugMessageSaver;

type
  TMainForm = class(TForm)
    StatusBar1: TStatusBar;
    Panel1: TPanel;
    Panel2: TPanel;
    Splitter1: TSplitter;
    Panel3: TPanel;
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    TabSheet2: TTabSheet;
    Panel4: TPanel;
    btnClearLog: TButton;
    memoLog: TMemo;
    popMenuLog: TPopupMenu;
    ActionList2: TActionList;
    EditCut1: TEditCut;
    EditCopy1: TEditCopy;
    EditSelectAll1: TEditSelectAll;
    EditDelete1: TEditDelete;
    T1: TMenuItem;
    C1: TMenuItem;
    D1: TMenuItem;
    N1: TMenuItem;
    A1: TMenuItem;
    EditUndo1: TEditUndo;
    U1: TMenuItem;
    btnSet: TButton;
    btnRead: TButton;
    btnOption: TButton;
    Panel5: TPanel;
    Panel6: TPanel;
    TreeView1: TTreeView;
    Label1: TLabel;
    Label2: TLabel;
    CheckBox1: TCheckBox;
    Label3: TLabel;
    panelOprat: TPanel;
    Panel8: TPanel;
    memoStatus: TMemo;
    Edit1: TEdit;
    Edit2: TEdit;
    Edit3: TEdit;
    procedure btnClearLogClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormDestroy(Sender: TObject);
    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    procedure btnOptionClick(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    FLogger: TDebugMessageSaver;
    procedure DoOptionChanged;
    procedure ShowOptions;
  public
    procedure AddLog(const Msg: AnsiString);
    procedure ShowStatus(const Msg: AnsiString);
  end;

var
  MainForm: TMainForm;

implementation

uses CmDialogTools, SetOptionFrm, uOptions;

{$R *.dfm}

procedure TMainForm.AddLog(const Msg: AnsiString);
begin
  memoLog.Lines.Add(Msg);
  FLogger.SaveDebugMessage(Msg);
end;

procedure TMainForm.btnClearLogClick(Sender: TObject);
begin
  memoLog.Clear;
end;

procedure TMainForm.FormCreate(Sender: TObject);
begin
  FLogger := TDebugMessageSaver.Create;
end;

procedure TMainForm.FormDestroy(Sender: TObject);
begin
  FLogger.Free;
end;

procedure TMainForm.FormCloseQuery(Sender: TObject; var CanClose: Boolean);
begin
  CanClose := (CmMessageDlg('是否关闭现场设置程序?',mtConfirmation,[mbYes,mbNo],0)=mrYes);
end;

procedure TMainForm.ShowStatus(const Msg: AnsiString);
begin
  memoStatus.Clear;
  memoStatus.Lines.Add(Msg);
end;

procedure TMainForm.btnOptionClick(Sender: TObject);
var
  setForm: TSetOptionForm;
begin
  setForm := TSetOptionForm.Create(nil);
  try
    if setForm.ShowModal=mrOK then
    begin
      DoOptionChanged;
    end;
  finally
    setForm.Free;
  end;
end;

procedure TMainForm.DoOptionChanged;
begin
  //todo change communication options
  ShowOptions;
end;

procedure TMainForm.ShowOptions;
var
  tmp: AnsiString;
begin
  tmp := '通讯端口:'+IntToStr(Options.CommPort)+
         ';  波特率:'+IntToStr(Options.CommBps)+
         ';  '+Options.CParity+
         ';  超时:'+IntToStr(Options.Timeout)+'毫秒';
  self.StatusBar1.Panels.Items[0].Text := tmp;
end;

procedure TMainForm.FormShow(Sender: TObject);
begin
  ShowOptions;
end;

end.
