package com.richrail.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.richrail.domain.Train;
import com.richrail.domain.Wagon;
import com.richrail.domain.WagonFactory;

public class WagonDAOImpl extends MySQLconfig implements WagonDAO{
	
	private WagonFactory factory;
	
	public WagonDAOImpl() {	
		this.factory = new WagonFactory();
	}
	
	public List<Wagon> getAllWagons() {
		List<Wagon> wagons = new ArrayList<Wagon>();
		
		try(Connection con = super.getConnection()) { 
			PreparedStatement pstmtWagon = con.prepareStatement("SELECT * FROM WAGON");
			ResultSet rsWagon = pstmtWagon.executeQuery();
		
			while (rsWagon.next()) {
				int wagonId = rsWagon.getInt("id");
				int wagonTrainId = rsWagon.getInt("train_id");
				String wagonType = rsWagon.getString("type");
				String wagonName = rsWagon.getString("name");
				
				Wagon wagon = factory.create(wagonType);
				wagon.setName(wagonName);
				wagon.setId(wagonId);
				wagon.setTrainId(wagonTrainId);
				wagon.setWagonType(wagonType);
				wagons.add(wagon);
			}
	
			rsWagon.close();
			closeConnection(pstmtWagon, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wagons;
	}
	
	private void closeConnection(PreparedStatement pstmt, Connection con) throws SQLException {
		pstmt.close();
		con.close();
	}

	@Override
	public void saveWagon(String name, int trainId, String type) {
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO WAGON (NAME, TYPE, SEATS, TRAIN_ID) VALUES (?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setInt(3, factory.seatsByType(type));
			pstmt.setInt(4, trainId);
			pstmt.executeUpdate();
			closeConnection(pstmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteWagon(int id) {
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM WAGON WHERE ID = ?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			System.out.print("Wagon id:" + id +"\n");
			System.out.print("In DAO delete\n");
			pstmt.close();
			closeConnection(pstmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void deleteWagon(String name) {
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM WAGON WHERE name = ?");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			closeConnection(pstmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	@Override
	public Wagon getWagon(int id) {
		Wagon wagon = null;
		try(Connection con = super.getConnection()) {
					
			PreparedStatement pstmtTwo = con.prepareStatement("SELECT * FROM WAGON WHERE ID = ?");
			pstmtTwo.setInt(1, id);
			ResultSet rsWagons = pstmtTwo.executeQuery();
			
			while(rsWagons.next()) {
				int wagonId = rsWagons.getInt("id");
				int wagonTrainId = rsWagons.getInt("train_id");
				String wagonType = rsWagons.getString("type");
				String wagonName = rsWagons.getString("name");
				
				wagon = factory.create(wagonType);
				wagon.setId(wagonId);
				wagon.setName(wagonName);
				wagon.setTrainId(wagonTrainId);
				wagon.setWagonType(wagonType);
			}
			pstmtTwo.close();
			closeConnection(pstmtTwo, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return wagon;
	}

	@Override
	public Wagon getWagon(String name) {
		Wagon wagon = null;
		try(Connection con = super.getConnection()) {
					
			PreparedStatement pstmtTwo = con.prepareStatement("SELECT * FROM WAGON WHERE NAME = ?");
			pstmtTwo.setString(1, name);
			ResultSet rsWagons = pstmtTwo.executeQuery();
			
			while(rsWagons.next()) {
				int wagonId = rsWagons.getInt("id");
				int wagonTrainId = rsWagons.getInt("train_id");
				String wagonType = rsWagons.getString("type");
				String wagonName = rsWagons.getString("name");
				
				wagon = factory.create(wagonType);
				wagon.setId(wagonId);
				wagon.setName(wagonName);
				wagon.setTrainId(wagonTrainId);
				wagon.setWagonType(wagonType);
			}
			pstmtTwo.close();
			closeConnection(pstmtTwo, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return wagon;
	}

}
	
	
