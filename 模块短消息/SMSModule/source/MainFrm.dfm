object MainForm: TMainForm
  Left = 416
  Top = 236
  Width = 567
  Height = 361
  Caption = #30701#28040#24687#25910#21457#22120
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 559
    Height = 41
    Align = alTop
    TabOrder = 0
    object btnStartStop: TButton
      Left = 16
      Top = 8
      Width = 75
      Height = 25
      Caption = #36816#34892
      TabOrder = 0
      OnClick = btnStartStopClick
    end
    object btnSetOptions: TButton
      Left = 104
      Top = 8
      Width = 75
      Height = 25
      Caption = #35774#32622
      TabOrder = 1
      OnClick = btnSetOptionsClick
    end
  end
  object logMemo: TMemo
    Left = 0
    Top = 41
    Width = 559
    Height = 286
    Align = alClient
    ScrollBars = ssVertical
    TabOrder = 1
  end
  object cmGsmModule1: TcmGsmModule
    Baudrate = 0
    Port = 0
    OnReceiveSmsMsg = cmGsmModule1ReceiveSmsMsg
    OnSentSmsMsgEx = cmGsmModule1SentSmsMsgEx
    Left = 480
    Top = 280
  end
  object OraSession1: TOraSession
    ConnectPrompt = False
    Options.CharLength = 3
    Options.Net = True
    Username = 'pss'
    Password = 'pss'
    Server = '115.238.74.114:1521:pss'
    BeforeConnect = OraSession1BeforeConnect
    Left = 16
    Top = 288
  end
  object orqSend: TOraQuery
    SQLDelete.Strings = (
      'DELETE FROM a_sms_send'
      'WHERE'
      '  ID = :ID')
    SQLUpdate.Strings = (
      'UPDATE a_sms_send'
      'SET'
      '  ID = :ID,'
      '  STATUS = :STATUS'
      'WHERE'
      '  ID = :OLD_ID')
    SQLLock.Strings = (
      'SELECT * FROM a_sms_send'
      'WHERE'
      '  ID = :ID'
      'FOR UPDATE NOWAIT')
    Session = OraSession1
    SQL.Strings = (
      'select * from a_sms_send'
      'where (case when status=2 then null else status end)=0')
    Left = 56
    Top = 288
    object orqSendID: TFloatField
      FieldName = 'ID'
    end
    object orqSendTELECODE: TStringField
      FieldName = 'TELECODE'
    end
    object orqSendCODEC: TIntegerField
      FieldName = 'CODEC'
      Required = True
    end
    object orqSendMSG: TStringField
      FieldName = 'MSG'
      Size = 280
    end
    object orqSendSTATUS: TIntegerField
      FieldName = 'STATUS'
      Required = True
    end
    object orqSendINSERTTIME: TDateTimeField
      FieldName = 'INSERTTIME'
      Required = True
    end
    object orqSendTAG: TStringField
      FieldName = 'TAG'
      Size = 50
    end
  end
  object oraReceive: TOraQuery
    SQLInsert.Strings = (
      'INSERT INTO a_sms_receive'
      '  (ID, TELECODE, CODEC, MSG, RECEIVETIME)'
      'VALUES'
      
        '  (seq_sms_receive.nextval, :TELECODE, :CODEC, :MSG, :RECEIVETIM' +
        'E)')
    Session = OraSession1
    SQL.Strings = (
      'select * from a_sms_receive')
    Left = 96
    Top = 288
    object oraReceiveID: TFloatField
      FieldName = 'ID'
      Required = True
    end
    object oraReceiveTELECODE: TStringField
      FieldName = 'TELECODE'
      Required = True
    end
    object oraReceiveCODEC: TIntegerField
      FieldName = 'CODEC'
      Required = True
    end
    object oraReceiveMSG: TStringField
      FieldName = 'MSG'
      Size = 280
    end
    object oraReceiveRECEIVETIME: TDateTimeField
      FieldName = 'RECEIVETIME'
      Required = True
    end
  end
  object CheckTimer: TTimer
    Enabled = False
    Interval = 5000
    OnTimer = CheckTimerTimer
    Left = 440
    Top = 280
  end
  object orqSent: TOraQuery
    SQLUpdate.Strings = (
      'UPDATE a_sms_send'
      'SET'
      '  STATUS = :STATUS'
      'WHERE'
      '  ID = :OLD_ID')
    Session = OraSession1
    SQL.Strings = (
      'select * from a_sms_send'
      'where id=:id')
    Left = 144
    Top = 288
    ParamData = <
      item
        DataType = ftUnknown
        Name = 'id'
      end>
    object orqSentID: TFloatField
      FieldName = 'ID'
    end
    object orqSentTELECODE: TStringField
      FieldName = 'TELECODE'
    end
    object orqSentCODEC: TIntegerField
      FieldName = 'CODEC'
      Required = True
    end
    object orqSentMSG: TStringField
      FieldName = 'MSG'
      Size = 280
    end
    object orqSentSTATUS: TIntegerField
      FieldName = 'STATUS'
      Required = True
    end
    object orqSentINSERTTIME: TDateTimeField
      FieldName = 'INSERTTIME'
      Required = True
    end
    object orqSentTAG: TStringField
      FieldName = 'TAG'
      Size = 50
    end
  end
end
