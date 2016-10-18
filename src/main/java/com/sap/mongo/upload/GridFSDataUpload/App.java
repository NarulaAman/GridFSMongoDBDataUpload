package com.sap.mongo.upload.GridFSDataUpload;

import java.io.File;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) throws IOException {
		mongoDB_GRIDFS("C:/Users/I852396/Documents/customer.csv");
	}
	public static void mongoDB_GRIDFS(String csvlocation) throws IOException{
		Mongo mongo = new MongoClient( "localhost:27017" ); // Connect to MongoDB
		System.out.println(mongo);
		DB db = mongo.getDB( "test" ); // Get database
		DBCollection collection = db.getCollection("customer");
		String bucketName = "BucketName";
		GridFS gridFs = new GridFS(db,bucketName); //Create instance of GridFS implementation  
		String imageName = "csvdata";
		upload(gridFs, csvlocation,imageName);
		
		
		  download(gridFs, imageName);
		//delete(gridFs, imageName);        

	}
	public static void upload(GridFS gridFs, String csvlocation, String imageName) throws IOException{
		GridFSInputFile gridFsInputFile = gridFs.createFile(new File(csvlocation));
		//gridFsInputFile.setId("777");
		gridFsInputFile.setFilename(imageName); //Set a name on GridFS entry
		gridFsInputFile.save(); //Save the file to MongoDB
	}
	public static void download(GridFS gridFs, String imageName) throws IOException{
		GridFSDBFile outputImageFile = gridFs.findOne(imageName);
		String outcsvLocation = "C:/Users/I852396/Documents/output.csv";//Location of the file read from MongoDB to be written
		outputImageFile.writeTo(new File(outcsvLocation));
	}
	public static void delete(GridFS gridFs, String imageName){
		gridFs.remove( gridFs.findOne(imageName) );
	}
}
