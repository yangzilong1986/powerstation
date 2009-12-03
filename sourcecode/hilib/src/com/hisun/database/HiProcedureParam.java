/*      */ package com.hisun.database;
/*      */ 
/*      */ class HiProcedureParam
/*      */ {
/*      */   String name;
/*      */   boolean out;
/*      */   String type;
/*      */ 
/*      */   public HiProcedureParam(String name, int columnType, String dataType)
/*      */   {
/* 1540 */     this.name = name;
/* 1541 */     this.out = HiJDBCProvider.isOutParam(columnType);
/*      */ 
/* 1543 */     this.type = dataType;
/*      */   }
/*      */ 
/*      */   public boolean isCursor() {
/* 1547 */     return "REF CURSOR".equals(this.type);
/*      */   }
/*      */ 
/*      */   public boolean isString() {
/* 1551 */     return (!(isCursor()));
/*      */   }
/*      */ }