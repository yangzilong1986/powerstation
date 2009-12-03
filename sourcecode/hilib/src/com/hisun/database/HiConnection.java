/*      */ package com.hisun.database;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ 
/*      */ class HiConnection
/*      */ {
/*      */   String name;
/*      */   Connection con;
/*      */ 
/*      */   HiConnection(String name, Connection con)
/*      */   {
/* 1533 */     this.name = name;
/* 1534 */     this.con = con;
/*      */   }
/*      */ }