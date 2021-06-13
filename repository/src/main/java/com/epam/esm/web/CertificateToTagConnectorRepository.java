package com.epam.esm.web;

public interface CertificateToTagConnectorRepository {
    int makeLink(int certId, int tagId);
    int deleteLink(int certId, int tagId);
    int deleteLink(int certId);
}
