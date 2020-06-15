package lab.web.converter;

import lab10.lab.core.domain.Client;
import lab.web.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto>{
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .serialNumber(client.getSerialNumber())
                .name(client.getName())
                .age(client.getAge())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}