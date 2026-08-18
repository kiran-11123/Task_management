package com.example.task_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.example.task_management.entity.Task;
import com.example.task_management.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void taskJsonSerializationShouldNotCauseCircularReference() {
		ObjectMapper objectMapper = new ObjectMapper();

		User user = new User();
		user.setId(1L);
		user.setName("Alice");
		user.setEmail("alice@example.com");
		user.setPassword("secret");

		Task task = new Task();
		task.setId(10L);
		task.setTitle("Write tests");
		task.setDescription("Serialize this object");
		task.setCompleted(false);
		task.setUser(user);

		user.setTasks(List.of(task));

		assertDoesNotThrow(() -> objectMapper.writeValueAsString(task));
	}

}
