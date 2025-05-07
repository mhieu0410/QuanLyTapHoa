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
        String sql = "UPDATE Products SET name = ?, category = ?, import_price = ?, sell_price = ?, quantity = ? , expiry_date = ? WHERE id = ? ";
        
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getImportPrice());
            ps.setDouble(4, p.getSellPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getExpiryDate());
            ps.setInt(7, p.getId());
            
            return ps.executeUpdate() > 0;
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
    
    public static List<Product> searchProduct(String keyword){
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE name LIKE ? OR category LIKE ?";
        
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));
                p.setImportPrice(rs.getDouble("import_price"));
                p.setSellPrice(rs.getDouble("sell_price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setExpiryDate(rs.getString("expiry_date"));
                
                list.add(p);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    
    public static boolean increaseQuantity(int productId, int addQty){
        String sql = "UPDATE Products SET quantity = quantity + ? WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addQty);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi cap nhat so luong: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean insertImportHistory(int productId, int qty, String supplier){
        String sql = "INSERT INTO ImportHistory(product_id, quantity, import_date, supplier) VALUES (?, ?, GETDATE(), ?)";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, qty);
            ps.setString(3, supplier);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi ghi nhap hang: " + e.getMessage());
        }
        return false;
    }
}
