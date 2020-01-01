package controllers;

import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.Activity;
import models.Location;
import models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

interface PacemakerInterface {
	@GET("/users")
	Call<List<User>> getUsers();
	
//	@GET("/users")
//	Call<List<User>> getFriends();

	@POST("/users")
	Call<User> registerUser(@Body User User);

	@GET("/users/{id}/activities")
	Call<List<Activity>> getActivities(@Path("id") String id);

	@POST("/users/{id}/activities")
	Call<Activity> addActivity(@Path("id") String id, @Body Activity activity);
	
	@GET("/users/{id}/friends")
	Call<List<User>> getFriends(@Path("id") String id);

	@PUT("/users/{id}/friend/{email}")
	Call<User> followFriend(@Path("id") String id, @Path("email") String email);

	@GET("/users/activities/friend/{email}")
	Call<List<Activity>> getFriendsActivities(@Path("email") String activityId);
	

	@GET("/users/{id}/activities/{activityId}")
	Call<Activity> getActivity(@Path("id") String id, @Path("activityId") String activityId);
	
	@GET("/users/{id}/friends/{id}/activities")
	Call<List<Activity>> listActivities(@Path("id") String id, String sortBy);

	@POST("/users/{id}/activities/{activityId}/locations")
	Call<Location> addLocation(@Path("id") String id, @Path("activityId") String activityId, @Body Location location);

	@DELETE("/users")
	Call<User> deleteUsers();

	@DELETE("/users/{id}")
	Call<User> deleteUser(@Path("id") String id);
	
	@DELETE("/users/{id}/friends/{id}")
	Call<User> deleteFriends(@Path("id") String id);

	@GET("/users/{id}")
	Call<User> getUser(@Path("id") String id);

	@DELETE("/users/{id}/activities")
	Call<String> deleteActivities(@Path("id") String id);

	@GET("/users/{id}/activities/{activityId}/locations")
	Call<List<Location>> getLocations(@Path("id") String id, @Path("activityId") String activityId);
}

public class PacemakerAPI {

	PacemakerInterface pacemakerInterface;

	public PacemakerAPI(String url) {
		Gson gson = new GsonBuilder().create();
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson))
				.build();
		pacemakerInterface = retrofit.create(PacemakerInterface.class);
	}

	public Collection<User> getUsers() {
		Collection<User> users = null;
		try {
			Call<List<User>> call = pacemakerInterface.getUsers();
			Response<List<User>> response = call.execute();
			users = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	public List<User> getFriends(String id) {
		List<User> users = null;
		try {
			Call<List<User>> call = pacemakerInterface.getFriends(id);
			Response<List<User>> response = call.execute();
			users = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	public User createUser(String firstName, String lastName, String email, String password) {
		User returnedUser = null;
		try {
			Call<User> call = pacemakerInterface.registerUser(new User(firstName, lastName, email, password));
			Response<User> response = call.execute();
			returnedUser = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnedUser;
	}

	public Activity createActivity(String id, String type, String location, double distance) {
		Activity returnedActivity = null;
		try {
			Call<Activity> call = pacemakerInterface.addActivity(id, new Activity(type, location, distance));
			Response<Activity> response = call.execute();
			returnedActivity = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnedActivity;
	}
//	public User addFriend(String email) {
//		User friend = null;
//		try {
//			Call<User> call = pacemakerInterface.addFriend(email, new User());
//			Response<User> response = call.execute();
//			friend = response.body();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return friend;
//	}

	public Activity getActivity(String userId, String activityId) {
		Activity activity = null;
		try {
			Call<Activity> call = pacemakerInterface.getActivity(userId, activityId);
			Response<Activity> response = call.execute();
			activity = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return activity;
	}

	public Collection<Activity> getActivities(String id) {
		Collection<Activity> activities = null;
		try {
			Call<List<Activity>> call = pacemakerInterface.getActivities(id);
			Response<List<Activity>> response = call.execute();
			activities = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return activities;
	}

	public List<Activity> listActivities(String userId, String sortBy) {
		List<Activity> activities = null;
		try {
			Call<List<Activity>> call = pacemakerInterface.listActivities(userId, sortBy);
			Response<List<Activity>> response = call.execute();
			activities = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return activities;
	}

	public List<Activity> listFriendActivities(String email) {
		List<Activity> activities = null;
		try {
			Call<List<Activity>> call = pacemakerInterface.getFriendsActivities(email);
			Response<List<Activity>> response = call.execute();
			activities = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return activities;
	}

	public void addLocation(String id, String activityId, double latitude, double longitude) {
		try {
			Call<Location> call = pacemakerInterface.addLocation(id, activityId, new Location(latitude, longitude));
			call.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public User getUserByEmail(String email) {
		Collection<User> users = getUsers();
		User foundUser = null;
		for (User user : users) {
			if (user.email.equals(email)) {
				foundUser = user;
			}
		}
		return foundUser;
	}

	public User getUser(String id) {
		User user = null;
		try {
			Call<User> call = pacemakerInterface.getUser(id);
			Response<User> response = call.execute();
			user = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	public void deleteUsers() {
		try {
			Call<User> call = pacemakerInterface.deleteUsers();
			call.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public User deleteUser(String id) {
		User user = null;
		try {
			Call<User> call = pacemakerInterface.deleteUser(id);
			Response<User> response = call.execute();
			user = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	public void deleteActivities(String id) {
		try {
			Call<String> call = pacemakerInterface.deleteActivities(id);
			call.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Location> getLocations(String id, String activityId) {
		List<Location> locations = null;
		try {
			Call<List<Location>> call = pacemakerInterface.getLocations(id, activityId);
			Response<List<Location>> response = call.execute();
			locations = response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return locations;
	}

	public Collection<User> deleteFriends(String id) {
		// TODO Auto-generated method stub
		Collection<User> user = null;
		try {
			Call<User> call = pacemakerInterface.deleteFriends(id);
			Response<User> response = call.execute();
			user = (Collection<User>) response.body();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	public void  followFriend(String id, String email){

//		User returnedUser = null;
		try {
			pacemakerInterface.followFriend(id, email).execute();
//			Response<User> response = call.execute();
//			returnedUser = response.body();
			System.out.println("Ok");
		} catch (Exception e) {
			System.out.println("Ok");
//			System.out.println(e.getMessage());
		}

	}


}
