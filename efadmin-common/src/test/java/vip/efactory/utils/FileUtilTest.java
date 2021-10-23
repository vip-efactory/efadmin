package vip.efactory.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilTest {

    @Test
    public void testToFile() {
        long retval = FileUtil.toFile(new MockMultipartFile("foo", (byte[]) null)).getTotalSpace();
        assertEquals(500695072768L, retval);
    }

    @Test
    public void testGetExtensionName() {
        assertEquals("foo", FileUtil.getExtensionName("foo"));
        assertEquals("exe", FileUtil.getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        assertEquals("foo", FileUtil.getFileNameNoEx("foo"));
        assertEquals("bar", FileUtil.getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        assertEquals("1000B   ", FileUtil.getSize(1000));
        assertEquals("1.00KB   ", FileUtil.getSize(1024));
        assertEquals("1.00MB   ", FileUtil.getSize(1048576));
        assertEquals("1.00GB   ", FileUtil.getSize(1073741824));
    }
}
