object SetOptionForm: TSetOptionForm
  Left = 419
  Top = 464
  BorderStyle = bsDialog
  Caption = #21442#25968#35774#32622
  ClientHeight = 269
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
    Height = 217
    Align = alTop
    TabOrder = 0
    object GroupBox2: TGroupBox
      Left = 16
      Top = 16
      Width = 489
      Height = 83
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
        Left = 198
        Top = 28
        Width = 36
        Height = 12
        Caption = #27874#29305#29575
      end
      object Label8: TLabel
        Left = 24
        Top = 54
        Width = 60
        Height = 12
        Caption = #30701#28040#24687#20013#24515
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
        Left = 248
        Top = 24
        Width = 145
        Height = 20
        Style = csDropDownList
        ItemHeight = 12
        TabOrder = 1
        Items.Strings = (
          '9600bps'
          '19200bps'
          '115200bps')
        Values.Strings = (
          '9600'
          '19200'
          '115200')
      end
      object edtSMSC: TJvEdit
        Left = 96
        Top = 50
        Width = 121
        Height = 20
        TabOrder = 2
        Text = 'edtSMSC'
      end
    end
    object GroupBox1: TGroupBox
      Left = 16
      Top = 111
      Width = 489
      Height = 89
      Caption = #25968#25454#28304
      TabOrder = 1
      object Label1: TLabel
        Left = 24
        Top = 24
        Width = 36
        Height = 12
        Caption = 'IP'#22320#22336
      end
      object Label2: TLabel
        Left = 224
        Top = 24
        Width = 24
        Height = 12
        Caption = #31471#21475
      end
      object Label3: TLabel
        Left = 336
        Top = 24
        Width = 18
        Height = 12
        Caption = 'SID'
      end
      object Label6: TLabel
        Left = 24
        Top = 56
        Width = 36
        Height = 12
        Caption = #29992#25143#21517
      end
      object Label7: TLabel
        Left = 224
        Top = 56
        Width = 48
        Height = 12
        Caption = #30331#24405#21475#20196
      end
      object edtOraIpAddress: TJvIPAddress
        Left = 72
        Top = 20
        Width = 137
        Height = 20
        AddressValues.Address = 0
        AddressValues.Value1 = 0
        AddressValues.Value2 = 0
        AddressValues.Value3 = 0
        AddressValues.Value4 = 0
        ParentColor = False
        TabOrder = 0
        Text = '0.0.0.0'
      end
      object edtOraPort: TJvValidateEdit
        Left = 256
        Top = 20
        Width = 57
        Height = 20
        CriticalPoints.MaxValueIncluded = False
        CriticalPoints.MinValueIncluded = False
        TabOrder = 1
      end
      object edtOraSID: TJvEdit
        Left = 360
        Top = 20
        Width = 89
        Height = 20
        TabOrder = 2
        Text = 'edtOraSID'
      end
      object edtOraUser: TJvEdit
        Left = 72
        Top = 52
        Width = 121
        Height = 20
        TabOrder = 3
        Text = 'edtOraUser'
      end
      object edtOraPwd: TJvEdit
        Left = 280
        Top = 52
        Width = 121
        Height = 20
        PasswordChar = '*'
        TabOrder = 4
        Text = 'edtOraPwd'
      end
    end
  end
  object Button1: TButton
    Left = 347
    Top = 229
    Width = 75
    Height = 25
    Caption = #30830#35748
    ModalResult = 1
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 435
    Top = 229
    Width = 75
    Height = 25
    Caption = #21462#28040
    ModalResult = 2
    TabOrder = 2
  end
end
