package com.rezeptapp.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // ✅ Test-Profil aktivieren
class RezeptappBackendApplicationTests {

	@Test
	void contextLoads() {
		// Startet den Spring-Kontext
	}
}