package com.save.mangrove;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

public class DigitalSignatureImplementor {

	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException, IOException {

String transactionId="qw224334ererfd34590MNm";
Date date=new Date();
int currentDate=date.getDate();
String currDate=currentDate+"/"+date.getMonth()+"/"+date.getYear();
System.out.println(currDate);
String imput ="Hi how are you";//currDate+transactionId;
/*String extraDetails=""*/

/*byte[] privateKeyBytes=new byte[100];
byte[] publicKeyBytes=new byte[100];
  KeyFactory kf = KeyFactory.getInstance("RSA");
PrivateKey privateKey = kf.generatePrivate(new X509EncodedKeySpec(privateKeyBytes));
PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));*/

KeyPairGenerator kpg=KeyPairGenerator.getInstance("DSA");
kpg.initialize(2048);
KeyPair kp = kpg.generateKeyPair();

Key pub = kp.getPublic();
PrivateKey  pvt = kp.getPrivate();

System.out.println(pvt);
Signature signature=Signature.getInstance("SHA256withDSA");
signature.initSign(pvt);
/*byte[] inputStreamByte=toByteStream(imput);
System.out.println(inputStreamByte);

signature.update(inputStreamByte);*/


//file where the digital signature will be stored 
File file =new File("C:\\Users\\pankajtandakar\\eclipse-workspace\\Db2Actice\\SignFile.file");
if(file.exists()) {
	System.out.println("yes it exists");
}
//adding data to the signature object
signature.update(imput.getBytes());

OutputStream out = null;
try {
    out = new FileOutputStream(file);
    //getting the signature bytes of the updated data
    byte[] signatureData = signature.sign();
    System.out.println("signature data::"+signatureData);
    out.write(signatureData);
} finally {
    if ( out != null ) out.close();
}
	}
	
	
	
	//here data in argument refwers to the data corresponding to which the digital signature is generated
	public boolean isSignatureVerified(PublicKey publicKey,byte[] digitalSignature,byte[] data) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature sign = Signature.getInstance("SHA256withDSA");
		sign.initVerify(publicKey);

sign.update(data);
		
		return sign.verify(digitalSignature);
	}
}
