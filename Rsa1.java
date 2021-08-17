import java.math.*;
import java.security.SecureRandom;
public class Rsa1 {
	private static final BigInteger one=new BigInteger("1");
	private static final SecureRandom random=new SecureRandom(); 
	private BigInteger PublicKey;
	private static BigInteger PrivateKey;
	private BigInteger modulus;
	private BigInteger phi;
	private static final int N=50;
	
	public Rsa1()
	{
		BigInteger p=BigInteger.probablePrime(N/2,random);
        BigInteger q=BigInteger.probablePrime(N/2,random);
        modulus =p.multiply(q);
        BigInteger x=p.subtract(one);
        BigInteger y=q.subtract(one);
        phi=x.multiply(y);
        PublicKey=new BigInteger("65537");
        PrivateKey= PublicKey.modInverse(phi);

     }
	
	public BigInteger encrpyt (BigInteger original)
	{
		BigInteger encrypted=original.modPow(PublicKey,modulus);
        return encrypted;		
	}
	
	public BigInteger decrypt(BigInteger encrypted)
	{
		BigInteger decrypted=encrypted.modPow(PrivateKey,modulus);
				return decrypted;
				
	}
	public static BigInteger getPrivateKey()
	{
		return PrivateKey;
		
	}
	public BigInteger getmodulus()
	{
		return modulus;
		
	}
	}


