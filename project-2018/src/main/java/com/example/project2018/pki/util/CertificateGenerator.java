package com.example.project2018.pki.util;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.ReasonFlags;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;


@Component
public class CertificateGenerator {
	public CertificateGenerator() {}
	//issuer je izdavac sertifikata a subject je onaj koji prima sertifikat
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500Name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getKeyPair().getPublic());
			//Parametar CA, true ili false
			certGen.addExtension(Extension.basicConstraints, false, new BasicConstraints(subjectData.isCA()));
			//URL za AIA u formatu "http://localhost:8080/certificate/{SERIJSKIBROJ}" vraca trazeni sertifikat
			GeneralName generalNameAIA = new GeneralName(GeneralName.uniformResourceIdentifier, new DERIA5String(subjectData.getAia()));
			AccessDescription accessDescription = new AccessDescription(AccessDescription.id_ad_caIssuers, generalNameAIA);
			AuthorityInformationAccess aia = new AuthorityInformationAccess(accessDescription);
			certGen.addExtension(Extension.authorityInfoAccess, false, aia);
			
			//CRP putanja do DRL fajla
			GeneralName generalNameCDP = new GeneralName(GeneralName.uniformResourceIdentifier, new DERIA5String(subjectData.getCdp()));
			DistributionPointName distributionPointName = new DistributionPointName(new GeneralNames(generalNameCDP) );
			DistributionPoint distributionPoint = new DistributionPoint(distributionPointName, new ReasonFlags(ReasonFlags.unused), null);
			//CRLDistPoint crlDistPoint = new CRLDistPoint(distributionPoint);
			
			
			//Extension ext1 = new Extension(Extension.basicConstraints,true, new DEROctetString(new BasicConstraints(subjectData.isCA())));
			//CRLDistPoint cdp = new CRLDistPoint();
			//ExtendedKeyUsage = new ExtendedKeyUsage(Extensions.)
			KeyUsage keyUsage = new KeyUsage(KeyUsage.dataEncipherment);
			//
			Extension extension = new Extension( Extension.keyUsage, true, keyUsage.getEncoded());
			//Extension extension = new Extension( Extension.keyUsage, true, new byte[] {16} );
			//Extension extension = new Extension( Extension.cRLDistributionPoints, true, new CRLDistPoint() );
			certGen.addExtension(extension);
			//certGen.addExtension(ext1);
			//*******************
			// Treba ubaciti parametar za AIA chasing. Za ovo se koristi endpoint koji je naveden pod tačkom 4. stavke specifikacije 2.1. 
			//certGen.addExtension(Extension.authorityInfoAccess, false, )
			// CDP polje, ovo treba da bude lokacija do CRL za izdavaoca datog sertifikata.
			//*******************
			
			//Generise se sertifikat koji je potpisan privatnim kljucem izdavaoca sertifikata
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
