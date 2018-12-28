package senior.design.group10.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.tv.Messages;

@Repository
public interface MessageDAO extends JpaRepository<Messages, Integer>
{
}
