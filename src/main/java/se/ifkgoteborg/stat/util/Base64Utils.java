package se.ifkgoteborg.stat.util;

/** Base64 encoding/decoding utilities
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revison:$
 */
public class Base64Utils
{
   private static final char[] base64Table =
   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz./".toCharArray();
   public static final String BASE64_ENCODING = "BASE64";
   public static final String BASE16_ENCODING = "HEX";

   // These functions assume that the byte array has MSB at 0, LSB at end.
   // Reverse the byte array (not the String) if this is not the case.
   // All base64 strings are in natural order, least significant digit last.
   public static String tob64(byte[] buffer)
   {
      boolean notleading = false;
      int len = buffer.length, pos = len % 3, c;
      byte b0 = 0, b1 = 0, b2 = 0;
      StringBuffer sb = new StringBuffer();

      switch(pos)
      {
         case 1:
            b2 = buffer[0];
            break;
         case 2:
            b1 = buffer[0];
            b2 = buffer[1];
            break;
      }
      do
      {
         c = (b0 & 0xfc) >>> 2;
         if(notleading || c != 0)
         {
            sb.append(base64Table[c]);
            notleading = true;
         }
         c = ((b0 & 3) << 4) | ((b1 & 0xf0) >>> 4);
         if(notleading || c != 0)
         {
            sb.append(base64Table[c]);
            notleading = true;
         }
         c = ((b1 & 0xf) << 2) | ((b2 & 0xc0) >>> 6);
         if(notleading || c != 0)
         {
            sb.append(base64Table[c]);
            notleading = true;
         }
         c = b2 & 0x3f;
         if(notleading || c != 0)
         {
            sb.append(base64Table[c]);
            notleading = true;
         }
         if(pos >= len)
            break;
         else
         {
            try
            {
               b0 = buffer[pos++];
               b1 = buffer[pos++];
               b2 = buffer[pos++];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
               break;
            }
         }
      } while(true);

      if(notleading)
         return sb.toString();
      else
         return "0";
   }

   public static byte[] fromb64(String str) throws NumberFormatException
   {
      int len = str.length();
      if(len == 0)
         throw new NumberFormatException("Empty Base64 string");

      byte[] a = new byte[len + 1];
      char c;
      int i, j;

      for(i = 0; i < len; ++i)
      {
         c = str.charAt(i);
         try
         {
            for(j = 0; c != base64Table[j]; ++j)
               ;
         } catch(Exception e)
         {
            throw new NumberFormatException("Illegal Base64 character");
         }
         a[i] = (byte) j;
      }

      i = len - 1;
      j = len;
      try
      {
         while(true)
         {
            a[j] = a[i];
            if(--i < 0)
               break;
            a[j] |= (a[i] & 3) << 6;
            --j;
            a[j] = (byte) ((a[i] & 0x3c) >>> 2);
            if(--i < 0)
               break;
            a[j] |= (a[i] & 0xf) << 4;
            --j;
            a[j] = (byte) ((a[i] & 0x30) >>> 4);
            if(--i < 0)
               break;
            a[j] |= (a[i] << 2);

            // Nasty, evil bug in Microsloth's Java interpreter under
            // Netscape:  The following three lines of code are supposed
            // to be equivalent, but under the Windows NT VM (Netscape3.0)
            // using either of the two commented statements would cause
            // the zero to be placed in a[j] *before* decrementing j.
            // Weeeeird.
            a[j-1] = 0; --j;
            // a[--j] = 0;
            // --j; a[j] = 0;

            if(--i < 0)
               break;
         }
      }
      catch(Exception e)
      {

      }

      try
      {
         while(a[j] == 0)
            ++j;
      }
      catch(Exception e)
      {
         return new byte[1];
      }

      byte[] result = new byte[len - j + 1];
      System.arraycopy(a, j, result, 0, len - j + 1);
      return result;
   }

}
