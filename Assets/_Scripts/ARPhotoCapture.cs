using UnityEngine;
using UnityEngine.UI;
using UnityEngine.XR.ARSubsystems;
using UnityEngine.XR.ARFoundation;
using System.IO;
using System.Collections;

public class ARPhotoCapture : MonoBehaviour
{
    [SerializeField] private Button captureButton;
    [SerializeField] private ARCameraManager cameraManager;

    private Texture2D photoTexture;

    private void Start()
    {
        captureButton.onClick.AddListener(CapturePhoto);
    }

    private void CapturePhoto()
    {
        cameraManager.requestedFacingDirection = CameraFacingDirection.World;

        StartCoroutine(TakePhoto());
    }

    private IEnumerator TakePhoto()
    {
        yield return new WaitForEndOfFrame();

        photoTexture = new Texture2D(Screen.width, Screen.height, TextureFormat.RGB24, false);
        photoTexture.ReadPixels(new Rect(0, 0, Screen.width, Screen.height), 0, 0);
        photoTexture.Apply();

        NativeGallery.SaveImageToGallery(photoTexture, "ARPhoto", "ARPhoto");

        cameraManager.requestedFacingDirection = CameraFacingDirection.User;
    }
}
