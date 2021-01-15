package com.mg.mongotemplate.core;

import com.mg.mongotemplate.config.SpringMongoConfig;
import com.mg.mongotemplate.model.Address;
import com.mg.mongotemplate.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class App {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoTemplate mongoOperation = (MongoTemplate) ctx.getBean("mongoTemplate");

		User user = new User("mani", "gayi");
		Address address = new Address();
		address.setCity("BVRM");
		address.setState("AP");
		Address addressOne = new Address();
		addressOne.setCity("PLK");
		addressOne.setState("AP");
		Address addressTwo = new Address();
		addressTwo.setCity("TNK");
		addressTwo.setState("AP");
		user.getAddressList().add(addressOne);
		user.getAddressList().add(address);
		user.getAddressList().add(addressTwo);
		// save
		mongoOperation.save(user);

		Query query = Query.query(Criteria.where("username").is("mani"));//.addCriteria(Criteria.where("addressList.city").is("PLK"));
		List<User> result = mongoOperation.find(query,User.class,"users");


		// update password
		mongoOperation.updateFirst(query, Update.update("password", "new password"),User.class);
		// find the updated user object
		User updatedUser = mongoOperation.findOne(new Query(Criteria.where("username").is("mani")), User.class);
		System.out.println("3. updatedUser : " + updatedUser);

//		mongoOperation.remove(updatedUser);

		// List, it should be empty now.
		List<User> listUser = mongoOperation.findAll(User.class);
		System.out.println("4. Number of user = " + listUser.size());

	}

}