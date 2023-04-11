using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.XR.ARFoundation;
using UnityEngine.XR.ARSubsystems;

using EnhancedTouch = UnityEngine.InputSystem.EnhancedTouch;

[RequireComponent(typeof(ARRaycastManager), 
    typeof(ARPlaneManager))]
public class PlaceObject : MonoBehaviour
{
    [SerializeField]
    private GameObject prefab;
    private Button captureButton;
    private ARRaycastManager aRRaycastManager;
    private ARPlaneManager aRPlaneManager;
    private List<ARRaycastHit> hits = new List<ARRaycastHit>();
    public int maxTaps = 1;
    private int tapCount = 0;

    private void Awake()
    {
        aRRaycastManager = GetComponent<ARRaycastManager>();
        aRPlaneManager = GetComponent<ARPlaneManager>();

        // Add a listener to the capture button to call the CaptureImage() method when clicked
        //captureButton.onClick.AddListener(CaptureImage);
    }

    private void Update()
    {
        if (Input.touchCount == 1)
        {
            Touch touch = Input.GetTouch(0);

            if (touch.phase == TouchPhase.Began)
            {
                if (aRRaycastManager.Raycast(touch.position, hits, TrackableType.PlaneWithinPolygon))
                {
                    Pose hitPose = hits[0].pose;

                    if (tapCount < maxTaps)
                    {
                        Instantiate(prefab, hitPose.position, hitPose.rotation);
                        tapCount++;
                    }
                }
            }
        }
    }

    //public void CaptureImage()
    //{
    //    // Take a screenshot and save it to the device's photo gallery
    //    string fileName = "ARImage_" + System.DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss") + ".png";
    //    ScreenCapture.CaptureScreenshot(fileName);
    //    StartCoroutine(SaveToGallery(fileName));
    //}

    //private IEnumerator SaveToGallery(string fileName)
    //{
    //    // Wait for the screenshot to be saved before trying to access it
    //    yield return new WaitForSeconds(1);

    //    // Get the path to the saved screenshot
    //    string path = Application.persistentDataPath + "/" + fileName;

    //    // Load the screenshot from the path and save it to the device's photo gallery
    //    Texture2D texture = new Texture2D(Screen.width, Screen.height, TextureFormat.RGB24, false);
    //    texture.filterMode = FilterMode.Trilinear;
    //    texture.LoadImage(File.ReadAllBytes(path));
    //    NativeGallery.Permission permission = NativeGallery.SaveImageToGallery(texture, "AR Images", fileName, (success, galleryPath) => {
    //        if (success)
    //        {
    //            Debug.Log("Image saved to gallery: " + galleryPath);
    //        }
    //        else
    //        {
    //            Debug.LogError("Failed to save image to gallery");
    //        }
    //    });

    //    // Destroy the temporary texture used to load the screenshot
    //    Destroy(texture);
    //}
    //public void Capture_image()
    //{
    //    ScreenCapture.CaptureScreenshot("shoepic.png");
    //}
}