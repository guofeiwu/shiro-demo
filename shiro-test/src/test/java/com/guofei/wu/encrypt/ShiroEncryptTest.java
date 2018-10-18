package com.guofei.wu.encrypt;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 吴国飞 (guofei.wu)
 * @version 2018/10/18
 * @since 2018/10/18
 */
public class ShiroEncryptTest {


    /**
     * base64 加密
     *
     * @since 2018/10/18
     */
    @Test
    public void encryptBase64Test() {
        String str = "hello";
        String encode = Base64.encodeToString(str.getBytes());
        String decode = Base64.decodeToString(encode);
        Assert.assertEquals(decode, str);
    }


    /**
     * 16进制加密
     *
     * @since 2018/10/18
     */
    @Test
    public void encryptHexTest() {
        String str = "hello";
        char[] encode = Hex.encode(str.getBytes());
        byte[] decode = Hex.decode(encode);
        String newStr = new String(decode);
        Assert.assertEquals(newStr, str);
    }


    /**
     * md5 散列算法
     *
     * @since 2018/10/18
     */
    @Test
    public void md5Test() {
        String str = "hello";
        String salt = "world + world";
//        Md5Hash hash = new Md5Hash(str);
        Md5Hash hash = new Md5Hash(str, salt);
        hash.setIterations(1);
        String encrypt = hash.toString();
        System.out.println(encrypt);
    }


    @Test
    public void sha256Test() {
        String str = "hello";
        String salt = "world";
        Sha256Hash hash = new Sha256Hash();
        hash.setBytes(str.getBytes());
        hash.setSalt(ByteSource.Util.bytes(salt));
        String encrypt = hash.toString();
        System.out.println(encrypt);

    }

    @Test
    public void hashServiceTest() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);

        SecureRandomNumberGenerator randomNumberGenerator =
                new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
//        String hex = randomNumberGenerator.nextBytes().toHex();
    }




}
