package com.nouria.flopBox.repo;

import com.nouria.flopBox.models.FtpServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FtpServerRepository extends JpaRepository<FtpServer, Long> {
}
