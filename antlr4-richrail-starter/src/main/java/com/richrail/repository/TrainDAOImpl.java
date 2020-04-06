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

public class TrainDAOImpl extends MySQLconfig implements TrainDAO{

	private WagonFactory factory;
	
	public TrainDAOImpl() {
		this.factory = new WagonFactory();
	}
	@Override
	public List<Train> getAll() {
		List<Train> trains = new ArrayList<Train>() ;
		List<Wagon> wagons = new ArrayList<Wagon>();
		
		try(Connection con = super.getConnection()) { 
			PreparedStatement pstmtTrain = con.prepareStatement("SELECT * FROM TRAIN");
			PreparedStatement pstmtWagon = con.prepareStatement("SELECT * FROM WAGON");
			ResultSet rsTrain = pstmtTrain.executeQuery();
			ResultSet rsWagon = pstmtWagon.executeQuery();
			while (rsTrain.next()) {
				Train train = new Train(rsTrain.getString("name"));
				train.setId(rsTrain.getInt("id"));
				trains.add(train);
			}

			while (rsWagon.next()) {
				int wagonId = rsWagon.getInt("id");
				int wagonTrainId = rsWagon.getInt("train_id");
				String wagonType = rsWagon.getString("type");
				String wagonName = rsWagon.getString("name");

				Wagon wagon = factory.create(wagonType);
				wagon.setId(wagonId);
				wagon.setName(wagonName);
				wagon.setTrainId(wagonTrainId);
				wagon.setWagonType(wagonType);
				wagons.add(wagon);

				for (Train t : trains) {
					if (t.getId() == wagon.getTrainId()) {
						t.getWagons().add(wagon);
					}
				}
			}
			rsTrain.close();
			rsWagon.close();
			pstmtTrain.close();
			closeConnection(pstmtWagon, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trains;
	}
	
	private void closeConnection(PreparedStatement pstmt, Connection con) throws SQLException {
		pstmt.close();
		con.close();
	}

	@Override
	public void saveTrain(String name) {
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO TRAIN (NAME) VALUES (?)");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			closeConnection(pstmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTrain(String name, int id) {
		try(Connection con = super.getConnection()) {
			PreparedStatement pstmtOne = con.prepareStatement("DELETE FROM TRAIN WHERE NAME = ?");
			pstmtOne.setString(1, name);
			pstmtOne.executeUpdate();
			
			PreparedStatement pstmtTwo = con.prepareStatement("DELETE FROM WAGON WHERE TRAIN_ID = ?");
			pstmtTwo.setInt(1, id);
			pstmtTwo.executeUpdate();
			pstmtTwo.close();
			closeConnection(pstmtOne, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTrain(int trainId) {
		try(Connection con = super.getConnection()) {
			PreparedStatement pstmtOne = con.prepareStatement("DELETE FROM TRAIN WHERE ID = ?");
			pstmtOne.setInt(1, trainId);
			pstmtOne.executeUpdate();
			
			PreparedStatement pstmtTwo = con.prepareStatement("DELETE FROM WAGON WHERE TRAIN_ID = ?");
			pstmtTwo.setInt(1, trainId);
			pstmtTwo.executeUpdate();
			pstmtTwo.close();
			closeConnection(pstmtOne, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Train getTrain(String name) {
		Train train = new Train(name);
		try(Connection con = super.getConnection()) {
			PreparedStatement pstmtOne = con.prepareStatement("SELECT * FROM TRAIN WHERE NAME = ?");
			pstmtOne.setString(1,name);
			ResultSet rsTrain = pstmtOne.executeQuery();
			
			while (rsTrain.next()) {
				train.setId(rsTrain.getInt("id"));
			}
			
			PreparedStatement pstmtTwo = con.prepareStatement("SELECT * FROM WAGON WHERE TRAIN_ID = ?");
			pstmtTwo.setInt(1, train.getId());
			ResultSet rsWagons = pstmtTwo.executeQuery();
			
			while(rsWagons.next()) {
				int wagonId = rsWagons.getInt("id");
				int wagonTrainId = rsWagons.getInt("train_id");
				String wagonType = rsWagons.getString("type");
				String wagonName = rsWagons.getString("name");
				
				Wagon wagon = factory.create(wagonType);
				wagon.setId(wagonId);
				wagon.setName(wagonName);
				wagon.setTrainId(wagonTrainId);
				wagon.setWagonType(wagonType);
				train.addWagon(wagon);
			}
			
			pstmtOne.close();
			pstmtTwo.close();
			closeConnection(pstmtTwo, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return train;
	}

}
