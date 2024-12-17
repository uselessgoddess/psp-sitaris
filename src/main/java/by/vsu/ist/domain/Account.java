package by.vsu.ist.domain;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class Account extends Entity {
	public String name;
	public byte[] photo = {};


	@Override
	public String toString() {
		return "Account{" +
				"id=" + getId() +
				", name='" + name + '\'' +
				", photo='" + photo.length + " bytes" + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getBase64Photo() {
		if (photo == null) {
			return "";
		}

		byte[] encodeBase64 = Base64.encodeBase64(getPhoto());
		return new String(encodeBase64, StandardCharsets.UTF_8);
	}

	public void setBase64Photo(String base64) {
		setPhoto(Base64.decodeBase64(base64));
	}
}
