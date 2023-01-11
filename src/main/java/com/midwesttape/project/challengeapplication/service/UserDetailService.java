package com.midwesttape.project.challengeapplication.service;


import com.midwesttape.project.challengeapplication.model.Address;
import com.midwesttape.project.challengeapplication.model.ResponseTemplateVO;
import com.midwesttape.project.challengeapplication.model.User;
import com.midwesttape.project.challengeapplication.repository.AddressRepository;
import com.midwesttape.project.challengeapplication.repository.UserRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserDetailService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    public UserDetailService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }


    @Value("classpath:schema.graphqls")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private UserDataFetcher userDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {
        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .type("Query", typeWiring -> typeWiring
                .dataFetcher("user", userDataFetcher))
            .build();
    }
    public GraphQL getGraphQL() {
        return graphQL;
    }
    public ResponseTemplateVO getUserWithAddress(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Optional<User> user = userRepository.findById(userId);
        Long addressId = null;
        try {
            addressId = Long.valueOf(user.get().getAddressId());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        if (addressId != null) {
            Optional<Address> address = addressRepository.findById(addressId);
            vo.setAddress(address.get());
        }

        vo.setUser(user.get());

        return vo;
    }
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());
        existingUser.setUsername(user.getUsername());
        existingUser.setAddressId(user.getAddressId());
        return userRepository.save(existingUser);
    }


}