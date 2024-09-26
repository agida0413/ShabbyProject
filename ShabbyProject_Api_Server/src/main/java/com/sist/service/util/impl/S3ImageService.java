package com.sist.service.util.impl;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.service.util.ImageService;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
@Service
@RequiredArgsConstructor
public class S3ImageService implements ImageService{
	
	
	 // 최대 파일 크기 5MB 변수
    private static final long MAX_FILE_SIZE = 2* 1024 * 1024 ; 
    private final S3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    

    
    public String upload(MultipartFile image) {
    	 //버킷이름에 공백에 생기는 경우 방지 
    	 bucket=bucket.trim();
    	
        // 입력받은 이미지 파일이 빈 파일인지 검증
        if (image == null || image.isEmpty() || image.getOriginalFilename() == null) {
            throw new BadRequestException("파일이 비어있거나 이름이 없습니다.");
        }
        //파일 사이즈 검증 
        validateFileSize(image);
		
			
        
        // 이미지 업로드 메서드 호출
        try {
            return uploadImageToS3(image);
        } catch (BadRequestException e) {
			// TODO: handle exception
        	throw new BadRequestException(e.getMessage());
		}
        catch (InternerException e) {
			// TODO: handle exception
        	throw new InternerException(e.getMessage(),e.getErrorCode());
		}
        catch (SdkException e) {
            // S3 관련 SDK 예외 처리
        	  
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","S3에 파일 업로드 중 오류 발생");
        } catch (IOException e) {
            // 파일 읽기 중 IO 예외 처리
        	
        	   throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","파일 읽기 중 IO 오류 발생");
   
        } catch (Exception e) {
            // 기타 예외 처리
        	
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","이미지 업로드 중 오류 발생");
        }
    }
    //파일 확장자 검증 
    private void validateImageFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new BadRequestException("파일 이름이 비어있거나 null입니다.");
        }

        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BadRequestException("파일 확장자가 없습니다.");
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

        if (!allowedExtensions.contains(extension)) {
            throw new BadRequestException("허용되지 않는 파일 확장자입니다.(jpg,jpeg,png 파일만 가능)");
        }
    }

    //파일크기 서버측 검증 (한번더 ) 1차 : 클라이언트 , 2차 : 게시물서비스 
    private void validateFileSize(MultipartFile file) {
    	
        if (file.getSize() > MAX_FILE_SIZE) {
        	
            throw new BadRequestException("사진 파일의 용량이 너무 큽니다."); // 파일 크기 초과 오류 처리
        }
    }
    //실제 업로드 
    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) {
            throw new BadRequestException("파일 이름이 없습니다.");
        }
        //확장자 검증 
        validateImageFileExtension(originalFilename);

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        byte[] bytes = image.getBytes();

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(s3FileName)
                    .contentType("image/" + extension)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
        } catch (S3Exception e) {
            
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","S3에 파일 업로드 실패" );
        } catch (SdkException e) {
          
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요." ,"SDK 오류 발생");
        }

        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(s3FileName)).toExternalForm();
    }
    //파일 삭제 
    public void deleteImage(String imageAddress) {
   	 bucket=bucket.trim();
        String key = getKeyFromImageAddress(imageAddress);
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","S3에서 이미지 삭제 중 오류 발생");
        } catch (Exception e) {
            
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","이미지 삭제 중 오류 발생");
        }
    }
    //파일 삭제시 키값 가져오기
    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodedPath = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8.name());
            return decodedPath.startsWith("/") ? decodedPath.substring(1) : decodedPath;
        } catch (MalformedURLException e) {
            
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요." ,"잘못된 이미지 주소 형식입니다");
        } catch (UnsupportedEncodingException e) {

            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요." ,"URL 디코딩 중 오류 발생");
        }
          catch (Exception e) {
            
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","삭제 시 키를 가져오는 과정에서 오류 발생");
        }
    }
}
