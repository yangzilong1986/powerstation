package com.hisun.encrypt;


import com.hisun.crypt.Decryptor;
import com.hisun.crypt.Encryptor;
import com.hisun.crypt.des.DESCryptorFactory;

import java.io.*;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;


public class HiEncryptJar {
    private DES des = null;

    private String key = "hisun";

    private Decryptor decryptor = null;

    private Encryptor encryptor = null;


    public HiEncryptJar() throws Exception {

        DESCryptorFactory factory = new DESCryptorFactory();

        this.decryptor = factory.getDecryptor();

        this.decryptor.setKey(factory.getDefaultDecryptKey());

        this.encryptor = factory.getEncryptor();

        this.encryptor.setKey(factory.getDefaultEncryptKey());

    }


    public static void main(String[] args) throws Exception {

        HiEncryptJar encryptJar = new HiEncryptJar();

        boolean isEncrypt = true;

        if (args.length != 2) {

            throw new Exception("参数错误!!参数： jar文件名 [0-加密|1－解密]");

        }


        if (args[1].equals("1")) {

            isEncrypt = false;

        }

        if (isEncrypt) encryptJar.encrypt(args[0]);

        else encryptJar.decrypt(args[0]);

    }


    public void encrypt(String file) throws Exception {

        String bakFile;

        File f1 = new File(file);


        if (f1.getParent() == null) bakFile = "." + File.separator + "encrypt-temp.jar";

        else {

            bakFile = f1.getParent() + File.separator + "encrypt-temp.jar";

        }

        OutputStream os = new FileOutputStream(bakFile);

        InputStream is = new FileInputStream(file);

        encrypt(is, os);

        is.close();

        os.close();

        File f2 = new File(bakFile);

        HiDirectoryUtil.copyFile(f2, f1);

        f2.delete();

    }


    public void encrypt(InputStream in, OutputStream out) throws Exception {

        JarInputStream jin = new JarInputStream(in);

        JarOutputStream jout = new JarOutputStream(out, jin.getManifest());

        byte[] buffer = new byte[5];

        int len = 0;

        ZipEntry entry = jin.getNextEntry();

        for (; entry != null; entry = jin.getNextEntry())

            if (entry.isDirectory()) {

                jout.putNextEntry(entry);

                jout.closeEntry();

                jin.closeEntry();

            } else {

                int b;

                ZipEntry entry1 = new ZipEntry(entry.getName());

                jout.putNextEntry(entry1);


                if (entry.getName().endsWith(".class")) {

                    int j = 0;

                    jout.write(this.key.getBytes());

                    jin.read(buffer, 0, 5);

                    if (this.key.equalsIgnoreCase(new String(buffer, 0, 5))) {

                        throw new IOException("Already encrypt");

                    }

                    for (int i = 0; i < 5; ++i) {

                        if (j == this.key.length()) j = 0;

                        jout.write(buffer[i] ^ this.key.charAt(j));

                        ++j;

                    }

                    while ((b = jin.read()) != -1) {

                        if (j == this.key.length()) j = 0;

                        jout.write(b ^ this.key.charAt(j));

                        ++j;

                    }

                } else {

                    while ((b = jin.read()) != -1) {

                        jout.write(b);

                    }


                }


                jout.flush();

                jout.closeEntry();

                jin.closeEntry();

            }

        jin.close();

        jout.close();

    }


    public void decrypt(String file) throws Exception {

        File f1 = new File(file);

        String bakFile = null;

        if (f1.getParent() == null) bakFile = "." + File.separator + "decrypt-temp.jar";

        else {

            bakFile = f1.getParent() + File.separator + "decrypt-temp.jar";

        }

        OutputStream os = new FileOutputStream(bakFile);

        InputStream is = new FileInputStream(file);

        decrypt(is, os);

        is.close();

        os.close();


        File f2 = new File(bakFile);

        HiDirectoryUtil.copyFile(f2, f1);

        f2.delete();

    }


    public void decrypt(InputStream in, OutputStream out) throws Exception {

        JarInputStream jin = new JarInputStream(in);

        JarOutputStream jout = new JarOutputStream(out, jin.getManifest());

        byte[] buffer = new byte[1024];

        int len = 0;

        ZipEntry entry = jin.getNextEntry();


        for (; entry != null; entry = jin.getNextEntry())

            if (entry.isDirectory()) {

                jout.putNextEntry(entry);

                jout.closeEntry();

                jin.closeEntry();

            } else {

                ZipEntry entry1 = new ZipEntry(entry.getName());

                jout.putNextEntry(entry1);


                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                while ((len = jin.read(buffer, 0, buffer.length)) != -1) {

                    byteBuffer.write(buffer, 0, len);

                }

                buffer = byteBuffer.toByteArray();

                if (entry.getName().endsWith(".class")) jout.write(this.decryptor.decrypt(buffer, 6, buffer.length));

                else {

                    jout.write(buffer);

                }


                jout.flush();

                jout.closeEntry();

                jin.closeEntry();

            }

        jin.close();

        jout.close();

    }

}