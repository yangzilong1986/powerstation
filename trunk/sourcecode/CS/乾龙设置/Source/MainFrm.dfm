object MainForm: TMainForm
  Left = 611
  Top = 290
  Width = 870
  Height = 640
  Caption = #29616#22330#35774#32622#31243#24207
  Color = clBtnFace
  Font.Charset = ANSI_CHARSET
  Font.Color = clWindowText
  Font.Height = -12
  Font.Name = #23435#20307
  Font.Style = []
  OldCreateOrder = False
  WindowState = wsMaximized
  OnCloseQuery = FormCloseQuery
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 12
  object Splitter1: TSplitter
    Left = 185
    Top = 41
    Height = 546
  end
  object StatusBar1: TStatusBar
    Left = 0
    Top = 587
    Width = 862
    Height = 19
    Panels = <
      item
        Width = 50
      end>
  end
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 862
    Height = 41
    Align = alTop
    TabOrder = 1
    object btnSet: TButton
      Left = 24
      Top = 8
      Width = 75
      Height = 25
      Caption = #35774#32622
      TabOrder = 0
    end
    object btnRead: TButton
      Left = 104
      Top = 8
      Width = 75
      Height = 25
      Caption = #35835#21462
      TabOrder = 1
    end
    object btnOption: TButton
      Left = 216
      Top = 8
      Width = 75
      Height = 25
      Caption = #31995#32479#21442#25968
      TabOrder = 2
      OnClick = btnOptionClick
    end
  end
  object Panel2: TPanel
    Left = 0
    Top = 41
    Width = 185
    Height = 546
    Align = alLeft
    Caption = 'Panel2'
    Constraints.MaxWidth = 300
    Constraints.MinWidth = 100
    TabOrder = 2
    object Panel5: TPanel
      Left = 1
      Top = 1
      Width = 183
      Height = 399
      Align = alClient
      Caption = 'Panel5'
      TabOrder = 0
      object TreeView1: TTreeView
        Left = 1
        Top = 1
        Width = 181
        Height = 397
        Align = alClient
        Indent = 19
        TabOrder = 0
      end
    end
    object Panel6: TPanel
      Left = 1
      Top = 400
      Width = 183
      Height = 145
      Align = alBottom
      TabOrder = 1
      object Label1: TLabel
        Left = 15
        Top = 16
        Width = 48
        Height = 12
        Caption = #32456#31471#22320#22336
      end
      object Label2: TLabel
        Left = 15
        Top = 47
        Width = 60
        Height = 12
        Caption = #20445#23433#22120#22320#22336
      end
      object Label3: TLabel
        Left = 15
        Top = 78
        Width = 48
        Height = 12
        Caption = #27979#37327#28857#21495
      end
      object CheckBox1: TCheckBox
        Left = 15
        Top = 109
        Width = 97
        Height = 17
        Caption = #30452#36890#20445#23433#22120
        TabOrder = 0
      end
      object Edit1: TEdit
        Left = 88
        Top = 12
        Width = 78
        Height = 20
        TabOrder = 1
        Text = 'Edit1'
      end
      object Edit2: TEdit
        Left = 88
        Top = 43
        Width = 90
        Height = 20
        TabOrder = 2
        Text = 'Edit1'
      end
      object Edit3: TEdit
        Left = 88
        Top = 74
        Width = 42
        Height = 20
        TabOrder = 3
        Text = 'Edit1'
      end
    end
  end
  object Panel3: TPanel
    Left = 188
    Top = 41
    Width = 674
    Height = 546
    Align = alClient
    Caption = 'Panel3'
    TabOrder = 3
    object PageControl1: TPageControl
      Left = 1
      Top = 1
      Width = 672
      Height = 544
      ActivePage = TabSheet1
      Align = alClient
      TabOrder = 0
      object TabSheet1: TTabSheet
        Caption = #25805#20316
        object panelOprat: TPanel
          Left = 0
          Top = 0
          Width = 664
          Height = 492
          Align = alClient
          BevelOuter = bvNone
          Caption = 'panelOprat'
          TabOrder = 0
        end
        object Panel8: TPanel
          Left = 0
          Top = 492
          Width = 664
          Height = 25
          Align = alBottom
          BevelOuter = bvNone
          TabOrder = 1
          object memoStatus: TMemo
            Left = 0
            Top = 0
            Width = 664
            Height = 25
            Align = alClient
            ScrollBars = ssVertical
            TabOrder = 0
          end
        end
      end
      object TabSheet2: TTabSheet
        Caption = #36890#35759
        ImageIndex = 1
        object Panel4: TPanel
          Left = 0
          Top = 0
          Width = 664
          Height = 33
          Align = alTop
          BevelOuter = bvNone
          TabOrder = 0
          DesignSize = (
            664
            33)
          object btnClearLog: TButton
            Left = 580
            Top = 4
            Width = 75
            Height = 25
            Anchors = [akTop, akRight]
            Caption = #28165#38500
            TabOrder = 0
            OnClick = btnClearLogClick
          end
        end
        object memoLog: TMemo
          Left = 0
          Top = 33
          Width = 664
          Height = 484
          Align = alClient
          PopupMenu = popMenuLog
          ScrollBars = ssVertical
          TabOrder = 1
        end
      end
    end
  end
  object popMenuLog: TPopupMenu
    Left = 265
    Top = 170
    object T1: TMenuItem
      Action = EditCut1
    end
    object C1: TMenuItem
      Action = EditCopy1
    end
    object D1: TMenuItem
      Action = EditDelete1
    end
    object U1: TMenuItem
      Action = EditUndo1
    end
    object N1: TMenuItem
      Caption = '-'
    end
    object A1: TMenuItem
      Action = EditSelectAll1
    end
  end
  object ActionList2: TActionList
    Left = 329
    Top = 170
    object EditCut1: TEditCut
      Category = 'Edit'
      Caption = #21098#20999'(&T)'
      Hint = 'Cut|Cuts the selection and puts it on the Clipboard'
      ImageIndex = 0
      ShortCut = 16472
    end
    object EditCopy1: TEditCopy
      Category = 'Edit'
      Caption = #22797#21046'(&C)'
      Hint = 'Copy|Copies the selection and puts it on the Clipboard'
      ImageIndex = 1
      ShortCut = 16451
    end
    object EditSelectAll1: TEditSelectAll
      Category = 'Edit'
      Caption = #20840#36873'(&A)'
      Hint = 'Select All|Selects the entire document'
      ShortCut = 16449
    end
    object EditDelete1: TEditDelete
      Category = 'Edit'
      Caption = #21024#38500'(&D)'
      Hint = 'Delete|Erases the selection'
      ImageIndex = 5
      ShortCut = 46
    end
    object EditUndo1: TEditUndo
      Category = 'Edit'
      Caption = #25764#28040'(&U)'
      Hint = 'Undo|Reverts the last action'
      ImageIndex = 3
      ShortCut = 16474
    end
  end
end
