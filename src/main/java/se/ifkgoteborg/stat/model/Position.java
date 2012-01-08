package se.ifkgoteborg.stat.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import se.ifkgoteborg.stat.model.enums.PositionType;
import se.ifkgoteborg.stat.model.enums.Side;

@Entity
@Table(name="position")
public class Position {
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	private String code;
	
	@Enumerated(value=EnumType.STRING)
	private Side side;

	@Enumerated(value=EnumType.STRING)
	private PositionType positionType;
	
	public Position() {}
	
	public Position(String name, String code, Side side, PositionType positionType) {
		this.name = name;
		this.code = code;
		this.side = side;
		this.positionType = positionType;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}

	public PositionType getPositionType() {
		return positionType;
	}

	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}
	
	
	
}
