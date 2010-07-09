unit CmDialogTools;

interface

uses
  Dialogs, Windows, Forms, Controls;

function  CmMessageDlg(const Msg: string; DlgType: TMsgDlgType;
  Buttons: TMsgDlgButtons; HelpCtx: Longint): Integer;

procedure CmShowMessage(const Msg: string);

implementation

function  CmMessageDlg(const Msg: string; DlgType: TMsgDlgType;
  Buttons: TMsgDlgButtons; HelpCtx: Longint): Integer;
var
  Str: AnsiString;
  UType: WORD;
  CallMessageBox: Boolean;
  MsgRet: integer;
begin
  if HelpCtx=0 then
  begin
    uType := 0; //Ϊ��ȥ�����뾯�棬�������ã�
    case DlgType of
      mtWarning: begin
        Str := 'ע��';
        uType := MB_ICONWARNING;
      end;
      mtError: begin
        Str := '����';
        uType := MB_ICONERROR;
      end;
      mtInformation: begin
        Str := '��ʾ';
        uType := MB_ICONINFORMATION;
      end;
      mtConfirmation: begin
        Str := 'ȷ��';
        uType := MB_ICONQUESTION;
      end;
      mtCustom: begin
        Str := Application.Title;
      end;
    end;

    CallMessageBox := false;
    if Buttons=mbYesNoCancel then
    begin
      uType := uType+MB_YESNOCANCEL+MB_DEFBUTTON3;
      CallMessageBox := true;
    end
    else if Buttons=mbYesAllNoAllCancel then
    begin
      CallMessageBox := false;
    end
    else if Buttons = mbOKCancel then
    begin
      uType := uType+MB_OKCANCEL+MB_DEFBUTTON2;
      CallMessageBox := true;
    end
    else if Buttons = mbAbortRetryIgnore then
    begin
      uType := uType+MB_ABORTRETRYIGNORE+MB_DEFBUTTON2;
      CallMessageBox := true;
    end
    else if Buttons = mbAbortIgnore then
    begin
      uType := uType+MB_ABORTRETRYIGNORE+MB_DEFBUTTON2;
      CallMessageBox := true;
    end
    else if Buttons = [mbOK] then
    begin
      uType := uType+MB_OK;
      CallMessageBox := true;
    end
    else if Buttons = [mbYes,mbNo] then
    begin
      uType := uType+MB_YESNO+MB_DEFBUTTON2;
      CallMessageBox := true;
    end;
    if CallMessageBox then
    begin
      MsgRet := MessageBox(Application.Handle,PAnsiChar(Msg),PAnsiChar(Str),uType);
      case MsgRet of
        IDABORT: Result := mrAbort;
        IDCANCEL: Result := mrCancel;
        IDIGNORE: Result := mrIgnore;
        IDNO: Result := mrNo;
        IDOK: Result := mrOK;
        IDRETRY: Result := mrRetry;
        IDYES: Result := mrYes;
      else
        Result := mrNo;
      end;
    end
    else
      Result := MessageDlg(Msg,DlgType,Buttons,HelpCtx);
  end
  else
    Result := MessageDlg(Msg,DlgType,Buttons,HelpCtx);
end;

procedure CmShowMessage(const Msg: string);
begin
  CmMessageDlg(Msg,mtInformation,[mbOK],0);
end;

end.
