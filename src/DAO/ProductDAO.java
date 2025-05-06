/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author admin
 */
public class ProductDAO {
    public static List<Product> getAllProducts(){
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        
        try(Connection conn = DBUtils.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()){
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("import_price"),
                        rs.getDouble("sell_price"),
                        rs.getInt("quantity"),
                        rs.getString("expiry_date")
                );
                list.add(p);
            }
            
        } catch (Exception e) {
            System.out.println("Loi getAllProducts: " + e.getMessage());
        }
        return list;
    }

    public static boolean addProduct(Product p){
        String sql = "Insert INTO Products(name, category, import_price, sell_price, quantity, expiry_date) VALUES(?,?,?,?,?,?)";
        
        try(Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getImportPrice());
            ps.setDouble(4, p.getSellPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getExpiryDate());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("loi addProduct" + e.getMessage());
        }
        return false;
    }
    
    public static boolean updateProduct(Product p){
        String sql = "UPDATE Products SET name = ?, category = ?, import_price = ?, sell_price = ?, expiry_date = ? WHERE id = ? ";
        
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getImportPrice());
            ps.setDouble(4, p.getSellPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getExpiryDate());
            ps.setInt(7, p.getId());
        } catch (Exception e) {
            System.out.println("Loi updateProduct: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean deleteProduct(int id){
        String sql = "DELETE FROM Products WHERE id = ? ";
        
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi deleteProduct: " + e.getMessage());
        }
        return false;
    }
}
