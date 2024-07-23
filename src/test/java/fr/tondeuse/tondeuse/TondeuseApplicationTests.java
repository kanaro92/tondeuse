package fr.tondeuse.tondeuse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBatchTest
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TondeuseApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private Job tondeuseJob;

	@BeforeEach
	public void setUp() {
		jobLauncherTestUtils.setJob(tondeuseJob);
		File file = new File("output_test.txt");
		if (file.exists()) {
			try (FileWriter writer = new FileWriter(file, false)) {
				writer.write("");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Test
	public void testTondeuseJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder().toJobParameters());
		assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

		// Vérification des résultats
		File file = new File("output_test.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line1 = reader.readLine();
			String line2 = reader.readLine();
			assertEquals("1 3 N", line1);
			assertEquals("5 1 E", line2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils() {
		return new JobLauncherTestUtils();
	}
}
