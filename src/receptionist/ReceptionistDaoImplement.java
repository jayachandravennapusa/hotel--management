package receptionist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sqlacess.MySqlAcess;
import constants.Quries;
import constants.RoomPrices;
import services.GuestServices;

public class ReceptionistDaoImplement implements ReceptionistDao{
	Connection conn=MySqlAcess.getConnection();
	Statement statement=MySqlAcess.getStatement();
	
	
	@Override
	public void seeAvailableRooms() throws Exception {
	PreparedStatement preparedStatement=conn.prepareStatement(Quries.SEE_AVAILABLE_ROOMS);
	preparedStatement.setString(1, "AVAILABLE");
	ResultSet rs=preparedStatement.executeQuery();
	System.out.println("This rooms are currently Available:");
	while(rs.next()) {
		int roomNo=rs.getInt("Room_Number");
		if(roomNo>=1 && roomNo<=5)
		System.out.println(roomNo+" - NORMAL");
		if(roomNo>=11 && roomNo<=15)
			System.out.println(roomNo+" - Deluxe");
		if(roomNo>=21 && roomNo<=25)
			System.out.println(roomNo+" - Super Deluxe");
		if(roomNo>=31 && roomNo<=35)
			System.out.println(roomNo+" - Executive ROOM");
		
	}
	}

	
	@Override
	public void guestRegistration(String guestId,String guestName,
			long guestMobileNumber,String guestAddress,String password) throws Exception {
	PreparedStatement preparedStatement=conn.prepareStatement(Quries.GUEST_REGISTRATION);//register guest
	preparedStatement.setString(1, guestId);
	preparedStatement.setString(2, guestName);
	preparedStatement.setLong(3, guestMobileNumber);
	preparedStatement.setString(4, guestAddress);
	preparedStatement.setString(5, password);
	preparedStatement.executeUpdate();
	System.out.println("Hey! Guest Added Successfully!");
	}

	
	@Override
	public void bookRoom(String guestId,int roomNo,String idProof,String checkInDate,
			String checkOutDate,String reasonForStay) throws Exception {
		PreparedStatement preparedStatement=conn.prepareStatement(Quries.SELECT_GUESTID);
		preparedStatement.setString(1, guestId);
		ResultSet rs=preparedStatement.executeQuery();
		//if(rs.getString("guest_id").equals(guestId)){ //if user registered
		rs=statement.executeQuery("select status from rooms where room_number="+roomNo);
		rs.next();
		if(rs.getString("Status").equals("available")) { // if room available
		preparedStatement=conn.prepareStatement(Quries.UPDATE_ROOM_STATUS);//update room status to booked
		preparedStatement.setString(1, "Booked");
		preparedStatement.setInt(2, roomNo);
		preparedStatement.executeUpdate();
		preparedStatement=conn.prepareStatement(Quries.INSERT_BOOKING_INFO);//update booking_information
		preparedStatement.setString(1, guestId);
		preparedStatement.setInt(2, roomNo);
		preparedStatement.setString(3, idProof);
		preparedStatement.setString(4, checkInDate);
		preparedStatement.setString(5, checkOutDate);
		preparedStatement.setString(6, reasonForStay);
		preparedStatement.executeUpdate();
		preparedStatement=conn.prepareStatement(Quries.INSERT_BILL);
		preparedStatement.setString(1, guestId);
		preparedStatement.setInt(2, roomNo);
		preparedStatement.setString(3, "Room");
		preparedStatement.setDouble(4, checkPrice(roomNo));
		preparedStatement.executeUpdate();
		System.out.println("Hey! Room booked Successfully!");
		//}
		
		}
	}

	
	
	@Override
	public void guestServices() throws Exception {
		GuestServices gs=new GuestServices();
		gs.guestServices();
	}

	
	
	@Override
	public String guestBill(String guestId) throws Exception{
		PreparedStatement preparedStatement=conn.prepareStatement(Quries.GUEST_BILL);
		preparedStatement.setString(1, guestId);
		ResultSet rs=preparedStatement.executeQuery();
		rs.next();
		//System.out.println(rs.getString("SUM"));
		return rs.getString("SUM");
	}

	
	
	@Override
	public List<List<String>> bookingInfo() throws Exception {
		//PreparedStatement preparedStatement=conn.prepareStatement(Quries.SELECT_BOOKING_INFO);
		ResultSet rs=statement.executeQuery(Quries.SELECT_BOOKING_INFO);
		//ResultSetMetaData rsmd = rs.getMetaData();
		//String firstColumnName = rsmd.getColumnName(1);
		List<List<String>> al=new ArrayList<>();
		while(rs.next()) {
			List<String> a=new ArrayList<>();
			a.add(rs.getString("booking_id"));
			a.add(rs.getString("guest_id"));
			a.add(rs.getString("room_number"));
			a.add(rs.getString("id_proof"));
			a.add(rs.getString("checkin_date"));
			a.add(rs.getString("checkout_date"));
			a.add(rs.getString("reason_for_stay"));
			al.add(a);
			
		}
		return al;
	}
	
	
	
	public double checkPrice(int roomNo) {
		double price=0;
		if(roomNo>=1 && roomNo<=5)
			price=RoomPrices.NORMAL_ROOM_PRICE;
		if(roomNo>=11 && roomNo<=15)
			price=RoomPrices.DELUXE_ROOM_PRICE;
		if(roomNo>=21 && roomNo<=25)
			price=RoomPrices.SUPER_DELUXE_ROOM_PRICE;
		if(roomNo>=31 && roomNo<=35)
			price=RoomPrices.EXECUTIVE_ROOM_PRICE;
		return price;
	}
	
	
	public int checkGuest(String guestId)throws Exception 
	{
		PreparedStatement preparedStatement=conn.prepareStatement(Quries.SELECT_GUESTID);
		preparedStatement.setString(1, guestId);
		ResultSet rs=preparedStatement.executeQuery();
		if(rs.next()==false) {
			System.out.println("Oh no! user not registered..."); // check whether user exist or not
			return 0;
		}
		else
			return 1;
	}
	
	
	public int checkRoom(int roomNo) throws Exception 
	{
		PreparedStatement preparedStatement=conn.prepareStatement(Quries.GET_ROOM_STATUS);
		preparedStatement.setInt(1, roomNo);
		ResultSet rs=preparedStatement.executeQuery();
		rs.next();
		if(!rs.getString("Status").equals("available")) { 
			// if room not available
			System.out.println("Oh Ho! The room you are looking for is not available right now...");
			return 0;
		}
		return 1;
	}

}
