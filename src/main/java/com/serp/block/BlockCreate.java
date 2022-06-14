//==============================================================================
//  WARNING!!  This file is overwritten by the Block UI Styler while generating
//  the automation code. Any modifications to this file will be lost after
//  generating the code again.
//
//       Filename:  C:\UGplot\Block create\Blockcreate.java
//
//        This file was generated by the NX Block UI Styler
//        Created by: User
//              Version: NX 7.5
//              Date: 06-12-2022  (Format: mm-dd-yyyy)
//              Time: 16:50 (Format: hh-mm)
//
//==============================================================================

//==============================================================================
//  Purpose:  This TEMPLATE file contains JAVA source to guide you in the
//  construction of your Block application dialog. The generation of your
//  dialog file (.dlx extension) is the first step towards dialog construction
//  within NX.  You must now create a NX Open application that
//  utilizes this file (.dlx).
//
//  The information in this file provides you with the following:
//
//  1.  Help on how to load and display your Block UI Styler dialog in NX
//      using APIs provided in NXOpen.BlockStyler namespace
//  2.  The empty callback methods (stubs) associated with your dialog items
//      have also been placed in this file. These empty methods have been
//      created simply to start you along with your coding requirements.
//      The method name, argument list and possible return values have already
//      been provided for you.
//==============================================================================

//------------------------------------------------------------------------------
//These imports are needed for the following template code
//------------------------------------------------------------------------------
package com.serp.block;

import com.serp.message.ListingWindowImpl;
import nxopen.*;
import nxopen.blockstyler.BlockDialog;
import nxopen.blockstyler.PropertyList;
import nxopen.features.Block;
import nxopen.features.BlockFeatureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
//------------------------------------------------------------------------------
//Represents Block Styler application class
//------------------------------------------------------------------------------

@Service
public class BlockCreate implements BlockDialog.Initialize, BlockDialog.DialogShown, BlockDialog.Apply, BlockDialog.Ok, BlockDialog.Update {
    //class members
    public static Session theSession = null;
    public static UI theUI = null;
    static BlockCreate theBlockcreate;
    private String theDialogName;
    private nxopen.blockstyler.BlockDialog theDialog;
    private nxopen.blockstyler.UIBlock group0;// Block type: Group
    private nxopen.blockstyler.UIBlock selectedPoint;// Block type: Specify Point
    private nxopen.blockstyler.UIBlock blockButton;// Block type: Button
    //------------------------------------------------------------------------------
    //Bit Option for Property: SnapPointTypesEnabled
    //------------------------------------------------------------------------------
    public static final int SnapPointTypesEnabled_UserDefined = (1 << 0);
    public static final int SnapPointTypesEnabled_Inferred = (1 << 1);
    public static final int SnapPointTypesEnabled_ScreenPosition = (1 << 2);
    public static final int SnapPointTypesEnabled_EndPoint = (1 << 3);
    public static final int SnapPointTypesEnabled_MidPoint = (1 << 4);
    public static final int SnapPointTypesEnabled_ControlPoint = (1 << 5);
    public static final int SnapPointTypesEnabled_Intersection = (1 << 6);
    public static final int SnapPointTypesEnabled_ArcCenter = (1 << 7);
    public static final int SnapPointTypesEnabled_QuadrantPoint = (1 << 8);
    public static final int SnapPointTypesEnabled_ExistingPoint = (1 << 9);
    public static final int SnapPointTypesEnabled_PointonCurve = (1 << 10);
    public static final int SnapPointTypesEnabled_PointonSurface = (1 << 11);
    public static final int SnapPointTypesEnabled_PointConstructor = (1 << 12);
    public static final int SnapPointTypesEnabled_TwocurveIntersection = (1 << 13);
    public static final int SnapPointTypesEnabled_TangentPoint = (1 << 14);
    public static final int SnapPointTypesEnabled_Poles = (1 << 15);
    public static final int SnapPointTypesEnabled_BoundedGridPoint = (1 << 16);
    //------------------------------------------------------------------------------
    //Bit Option for Property: SnapPointTypesOnByDefault
    //------------------------------------------------------------------------------
    public static final int SnapPointTypesOnByDefault_UserDefined = (1 << 0);
    public static final int SnapPointTypesOnByDefault_Inferred = (1 << 1);
    public static final int SnapPointTypesOnByDefault_ScreenPosition = (1 << 2);
    public static final int SnapPointTypesOnByDefault_EndPoint = (1 << 3);
    public static final int SnapPointTypesOnByDefault_MidPoint = (1 << 4);
    public static final int SnapPointTypesOnByDefault_ControlPoint = (1 << 5);
    public static final int SnapPointTypesOnByDefault_Intersection = (1 << 6);
    public static final int SnapPointTypesOnByDefault_ArcCenter = (1 << 7);
    public static final int SnapPointTypesOnByDefault_QuadrantPoint = (1 << 8);
    public static final int SnapPointTypesOnByDefault_ExistingPoint = (1 << 9);
    public static final int SnapPointTypesOnByDefault_PointonCurve = (1 << 10);
    public static final int SnapPointTypesOnByDefault_PointonSurface = (1 << 11);
    public static final int SnapPointTypesOnByDefault_PointConstructor = (1 << 12);
    public static final int SnapPointTypesOnByDefault_TwocurveIntersection = (1 << 13);
    public static final int SnapPointTypesOnByDefault_TangentPoint = (1 << 14);
    public static final int SnapPointTypesOnByDefault_Poles = (1 << 15);
    public static final int SnapPointTypesOnByDefault_BoundedGridPoint = (1 << 16);

    //------------------------------------------------------------------------------
    //Constructor for NX Styler class
    //------------------------------------------------------------------------------
    public BlockCreate() throws Exception, RemoteException {
        try {
            theSession = (Session) SessionFactory.get("Session");
            theUI = (UI) SessionFactory.get("UI");
            theDialogName = "c:\\UGplot\\Block create\\Block create.dlx";
            theDialog = theUI.createDialog(theDialogName);
            theDialog.addApplyHandler(this);
            theDialog.addOkHandler(this);
            theDialog.addUpdateHandler(this);
            theDialog.addInitializeHandler(this);
            theDialog.addDialogShownHandler(this);
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            throw new Exception(ex);
        }
    }

    //------------------------------- DIALOG LAUNCHING ---------------------------------
    //
    //    Before invoking this application one needs to open any part/empty part in NX
    //    because of the behavior of the blocks.
    //
    //    Make sure the dlx file is in one of the following locations:
    //        1.) From where NX session is launched
    //        2.) $UGII_USER_DIR/application
    //        3.) For released applications, using UGII_CUSTOM_DIRECTORY_FILE is highly
    //            recommended. This variable is set to a full directory path to a file
    //            containing a list of root directories for all custom applications.
    //            e.g., UGII_CUSTOM_DIRECTORY_FILE=$UGII_ROOT_DIR\menus\custom_dirs.dat
    //
    //    You can create the dialog using one of the following way:
    //
    //    1. USER EXIT
    //
    //        1) Create the Shared Library -- Refer "Block UI Styler programmer's guide"
    //        2) Invoke the Shared Library through File->Execute->NX Open menu.
    //
    //------------------------------------------------------------------------------
    public void start() throws Exception {
        try {
            theBlockcreate = new BlockCreate();
            // The following method shows the dialog immediately
            theBlockcreate.show();
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        } finally {
            if (theBlockcreate != null) {
                theBlockcreate.dispose();
            }
        }
    }

    //------------------------------------------------------------------------------
    // This method specifies how a shared image is unloaded from memory
    // within NX. This method gives you the capability to unload an
    // internal NX Open application or user  exit from NX. Specify any
    // one of the three constants as a return value to determine the type
    // of unload to perform:
    //
    //
    //    Immediately : unload the library as soon as the automation program has completed
    //    Explicitly  : unload the library from the "Unload Shared Image" dialog
    //    AtTermination : unload the library when the NX session terminates
    //
    //
    // NOTE:  A program which associates NX Open applications with the menubar
    // MUST NOT use this option since it will UNLOAD your NX Open application image
    // from the menubar.
    //------------------------------------------------------------------------------
    public static final int getUnloadOption() {
        //return BaseSession.LibraryUnloadOption.EXPLICITLY;
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
        // return BaseSession.LibraryUnloadOption.ATTERMINATION;
    }

    //------------------------------------------------------------------------------
    // Following method cleanup any housekeeping chores that may be needed.
    // This method is automatically called by NX.
    //------------------------------------------------------------------------------
    public static void unloadLibrary() throws NXException, RemoteException {
        try {
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
    }

    //------------------------------------------------------------------------------
    //This method shows the dialog on the screen
    //------------------------------------------------------------------------------
    public int show() throws NXException, RemoteException {
        try {
            theDialog.show();
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
        return 0;
    }

    //------------------------------------------------------------------------------
    //Method Name: dispose
    //------------------------------------------------------------------------------
    public void dispose() throws NXException, RemoteException {
        if (theDialog != null) {
            theDialog.dispose();
            theDialog = null;
        }
    }

    //------------------------------------------------------------------------------
    //---------------------Block UI Styler Callback Functions--------------------------
    //------------------------------------------------------------------------------

    //------------------------------------------------------------------------------
    //Callback Name: initialize
    //------------------------------------------------------------------------------
    public void initialize() throws NXException, RemoteException {
        try {
            group0 = (nxopen.blockstyler.UIBlock) theDialog.topBlock().findBlock("group0");
            selectedPoint = (nxopen.blockstyler.UIBlock) theDialog.topBlock().findBlock("selectedPoint");
            blockButton = (nxopen.blockstyler.UIBlock) theDialog.topBlock().findBlock("blockButton");
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
    }

    //------------------------------------------------------------------------------
    //Callback Name: dialogShown
    //This callback is executed just before the dialog launch. Thus any value set
    //here will take precedence and dialog will be launched showing that value.
    //------------------------------------------------------------------------------
    public void dialogShown() throws NXException, RemoteException {
        try {
            //---- Enter your callback code here -----
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
    }

    //------------------------------------------------------------------------------
    //Callback Name: apply
    //Following callback is associated with the "theDialog" Block.
    //------------------------------------------------------------------------------
    public int apply() throws NXException, RemoteException {
        int errorCode = 0;
        try {
            //---- Enter your callback code here -----
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            errorCode = 1;
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
        return errorCode;
    }

    //------------------------------------------------------------------------------
    //Callback Name: update
    //Following callback is associated with the "theDialog" Block.
    //------------------------------------------------------------------------------
    public int update(nxopen.blockstyler.UIBlock block) throws NXException, RemoteException {
        try {
            if (block == selectedPoint) {
                //---------Enter your code here-----------
            } else if (block == blockButton) {
                //---------Enter your code here-----------
                createBlock();
            }
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
        return 0;
    }

    //------------------------------------------------------------------------------
    //Callback Name: ok
    //------------------------------------------------------------------------------
    public int ok() throws NXException, RemoteException {
        int errorCode = 0;
        try {
            //---- Enter your callback code here -----
            errorCode = apply();
        } catch (Exception ex) {
            //---- Enter your exception handling code here -----
            errorCode = 1;
            theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
        }
        return errorCode;
    }

    public void createBlock() throws RemoteException, NXException {
        if (selectedPoint != null) {
            try {
                Part part = theSession.parts().work();
                Block block = null;
                BlockFeatureBuilder blockFeatureBuilder = part.features().createBlockFeatureBuilder(block);
                blockFeatureBuilder.booleanOption().setType(
                        nxopen.geometricutilities.BooleanOperation.BooleanType.CREATE);
                PropertyList properties = selectedPoint.getProperties();
                Point3d point3d = properties.getPoint("Point");
                Point point = part.points().createPoint(point3d);
                blockFeatureBuilder.setOriginPoint(point);
                blockFeatureBuilder.setHeight("5");
                blockFeatureBuilder.setLength("5");
                blockFeatureBuilder.setWidth("5");
                blockFeatureBuilder.commit();
                blockFeatureBuilder.destroy();
            } catch (Exception e) {
                theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR,
                        "Error - " + e.getMessage());
            }
        }
    }
}
