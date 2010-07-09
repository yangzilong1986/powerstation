object SetOptionForm: TSetOptionForm
  Left = 419
  Top = 464
  BorderStyle = bsDialog
  Caption = #21442#25968#35774#32622
  ClientHeight = 231
  ClientWidth = 521
  Color = clBtnFace
  Font.Charset = ANSI_CHARSET
  Font.Color = clWindowText
  Font.Height = -12
  Font.Name = #23435#20307
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 12
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 521
    Height = 169
    Align = alTop
    TabOrder = 0
    object GroupBox2: TGroupBox
      Left = 16
      Top = 16
      Width = 489
      Height = 129
      Caption = #20018#21475#35774#32622
      TabOrder = 0
      object Label4: TLabel
        Left = 22
        Top = 26
        Width = 36
        Height = 12
        Caption = #31471#21475#21495
      end
      object Label5: TLabel
        Left = 22
        Top = 60
        Width = 36
        Height = 12
        Caption = #27874#29305#29575
      end
      object Label6: TLabel
        Left = 22
        Top = 96
        Width = 36
        Height = 12
        Caption = #26657#39564#20301
      end
      object Label1: TLabel
        Left = 264
        Top = 24
        Width = 24
        Height = 12
        Caption = #36229#26102
      end
      object edtCommPort: TJvValidateEdit
        Left = 72
        Top = 22
        Width = 65
        Height = 20
        CriticalPoints.MaxValueIncluded = False
        CriticalPoints.MinValueIncluded = False
        EditText = '1'
        HasMinValue = True
        MaxValue = 40.000000000000000000
        MinValue = 1.000000000000000000
        TabOrder = 0
      end
      object cmbCommBps: TLxchComboBoxStr
        Left = 72
        Top = 56
        Width = 145
        Height = 20
        Style = csDropDownList
        ItemHeight = 12
        TabOrder = 1
        Items.Strings = (
          '600bps'
          '1200bps'
          '2400bps'
          '4800bps'
          '9600bps'
          '19200bps')
        Values.Strings = (
          '600'
          '1200'
          '2400'
          '4800'
          '9600'
          '19200')
      end
      object cmbParity: TLxchComboBox
        Left = 72
        Top = 92
        Width = 145
        Height = 20
        Style = csDropDownList
        ItemHeight = 12
        ItemIndex = 4
        TabOrder = 2
        Text = #31354'('#24658'0)'
        Items.Strings = (
          #26080#26657#39564
          #22855#26657#39564
          #20598#26657#39564
          #26631#35760'('#24658'1)'
          #31354'('#24658'0)')
        Values.Strings = (
          '0'
          '1'
          '2'
          '3'
          '4')
        Value = 4
      end
      object edtTimeout: TJvValidateEdit
        Left = 295
        Top = 19
        Width = 66
        Height = 20
        CriticalPoints.MaxValueIncluded = False
        CriticalPoints.MinValueIncluded = False
        TabOrder = 3
      end
    end
  end
  object Button1: TButton
    Left = 347
    Top = 189
    Width = 75
    Height = 25
    Caption = #30830#35748
    ModalResult = 1
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 435
    Top = 189
    Width = 75
    Height = 25
    Caption = #21462#28040
    ModalResult = 2
    TabOrder = 2
  end
  object ckbShowAllIoInput: TCheckBox
    Left = 16
    Top = 176
    Width = 129
    Height = 17
    Caption = #26174#31034#25152#26377#25509#25910#23383#31526
    TabOrder = 3
  end
end
