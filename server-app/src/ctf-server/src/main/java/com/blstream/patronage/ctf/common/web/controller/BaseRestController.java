package com.blstream.patronage.ctf.common.web.controller;

import com.blstream.patronage.ctf.common.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * Copyright 2013 BLStream
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * User: mkr
 * Date: 1/16/13
 */
public abstract class BaseRestController<T, ID extends Serializable, S extends CrudService<T, ID>> extends AbstractRestController implements RestController<T, ID> {

    private static Logger logger = LoggerFactory.getLogger(BaseRestController.class);

    protected S service;

    public void setService(S service) {
        this.service = service;
    }

    public abstract String getIdFromResource(T resource);

    @Override
    public T create(@RequestBody T resource) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- create method invoked ----");
        }
        return service.create(resource);
    }

    @Override
    public T update(@PathVariable ID id, @RequestBody T resource) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- update method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        Assert.notNull(resource, "Resources cannot be null");

        return service.update(id, resource);
    }

    @Override
    public Iterable<T> findAll() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findAll method invoked ----");
        }

        return service.findAll();
    }

    @Override
    public T findById(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findById method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        return service.findById(id);
    }

    @Override
    public void delete(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- delete method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        service.delete(id);
    }

    @RequestMapping("/isAlive")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
    public @ResponseBody String isAlive() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- isAlive method invoked ----");
        }

        return "It's alive!";
    }
}
