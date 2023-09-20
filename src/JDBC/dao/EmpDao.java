package JDBC.dao;

import JDBC.common.Common;
import JDBC.vo.EmpVo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// DAO : Data Access Object, 데이터베이스에 접근해 데이터를 조회하거나 수정 위해 사용 (DML과 유사한 기능, DDL은 못함(테이블생성))
public class EmpDao {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
     public List<EmpVo> empSelect() {
         List<EmpVo> list = new ArrayList();
         try {
             conn = Common.getConnection();
             stmt = conn.createStatement();
             String sql = "SELECT * FROM EMP";
             rs = stmt.executeQuery(sql);  // executeQuery SELECT문에서 사용

             while(rs.next()) {  // 결과에서 읽을 내용이 있으면 True (테이블 행의 개수만큼 순회)
                 int empNo = rs.getInt("EMPNO");
                 String name = rs.getString("ENAME");
                 String job = rs.getString("JOB");
                 int mgr = rs.getInt("MGR");
                 Date date= rs.getDate("HIREDATE");
                 BigDecimal sal = rs.getBigDecimal("SAL");
                 BigDecimal comm = rs.getBigDecimal("COMM");
                 int deptNo = rs.getInt("DEPTNO");
                 list.add(new EmpVo(empNo, name, job, mgr, date, sal, comm, deptNo));
             }
             Common.close(rs);
             Common.close(stmt);
             Common.close(conn);

         } catch (Exception e) {
             e.printStackTrace();
         }
         return list;
     }
     public void empInsert() {
         System.out.println("사원 정보를 입력 하세요.");
         System.out.print("사원번호 : ");
         int no = sc.nextInt();
         System.out.print("이름 : ");
         String name = sc.next();
         System.out.print("직책 : ");
         String job = sc.next();
         System.out.print("상관 : ");
         int mgr = sc.nextInt();
         System.out.print("입사일 : ");
         String date = sc.next();
         System.out.print("급여 : ");
         BigDecimal sal = sc.nextBigDecimal();
         System.out.print("성과급 : ");
         BigDecimal comm = sc.nextBigDecimal();
         System.out.print("부서번호 : ");
         int deptNo = sc.nextInt();

         String sql = "INSERT INTO EMP(EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) VALUES(?,?,?,?,?,?,?,?)";
         try{
             conn = Common.getConnection();
             pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, no);
             pstmt.setString(2, name);
             pstmt.setString(3, job);
             pstmt.setInt(4,mgr);
             pstmt.setString(5, date);
             pstmt.setBigDecimal(6, sal);
             pstmt.setBigDecimal(7, comm);
             pstmt.setInt(8, deptNo);
             int rst = pstmt.executeUpdate();  // 실행 결과가 정수 값으로 반환됨 (영향을 받은 행의 개수가 반환됨) / executeUpdate : 반횐되는 값 1. 정수값 2. 영향 받은 행의 갯수

         } catch(Exception e) {
             e.printStackTrace();
         }
         Common.close(pstmt);
         Common.close(conn);

     }


     public void empSelectPrint(List<EmpVo> list) {
         for (EmpVo e : list) {
             System.out.print(e.getEmpNo() + " ");
             System.out.print(e.getName() + " ");
             System.out.print(e.getJob() + " ");
             System.out.print(e.getMgr() + " ");
             System.out.print(e.getDate() + " ");
             System.out.print(e.getSal() + " ");
             System.out.print(e.getComm() + " ");
             System.out.print(e.getDeptNo() + " ");
             System.out.println();
         }
     }
}
