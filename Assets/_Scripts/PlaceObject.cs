using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.ARFoundation;
using UnityEngine.XR.ARSubsystems;
using EnhancedTouch = UnityEngine.InputSystem.EnhancedTouch;

[RequireComponent(typeof(ARRaycastManager), 
    typeof(ARPlaneManager))]
public class PlaceObject : MonoBehaviour
{
    [SerializeField]
    private GameObject prefab;

    private ARRaycastManager aRRaycastManager;
    private ARPlaneManager aRPlaneManager;
    private List<ARRaycastHit> hits = new List<ARRaycastHit>();
    public int maxTaps = 1;
    private int tapCount = 0;

    private void Awake()
    {
        aRRaycastManager = GetComponent<ARRaycastManager>();
        aRPlaneManager = GetComponent<ARPlaneManager>();
    }

    //private void OnEnable()
    //{
    //    EnhancedTouch.TouchSimulation.Enable();
    //    EnhancedTouch.EnhancedTouchSupport.Enable();
    //    EnhancedTouch.Touch.onFingerDown += FingerDown;
    //}

    //private void OnDisable()
    //{
    //    EnhancedTouch.TouchSimulation.Disable();
    //    EnhancedTouch.EnhancedTouchSupport.Disable();
    //    EnhancedTouch.Touch.onFingerDown -= FingerDown;
    //}

    //private void FingerDown(EnhancedTouch.Finger finger)
    //{
    //    if (finger.index != 0) return;

    //    if (aRRaycastManager.Raycast(finger.currentTouch.screenPosition, hits, TrackableType.PlaneWithinPolygon)){
    //        foreach(ARRaycastHit hit in hits)
    //        {
    //            Pose pose = hit.pose;
    //            GameObject obj = Instantiate(prefab, pose.position, pose.rotation);
    //        }

    //    }

    //}
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
}