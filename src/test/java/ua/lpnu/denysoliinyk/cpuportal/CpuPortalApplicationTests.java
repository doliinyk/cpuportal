package ua.lpnu.denysoliinyk.cpuportal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(useMainMethod = UseMainMethod.ALWAYS)
class CpuPortalApplicationTests {
    @Test
    void contextLoads(ApplicationContext applicationContext) {
        assertThat(applicationContext).isNotNull();
    }
}
