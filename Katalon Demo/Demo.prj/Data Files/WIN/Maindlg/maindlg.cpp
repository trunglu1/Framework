
/*
maindlg.exe - WIN32 application with a dialof for main window
Copyright (C) 2000  Antti Markus (antti@pld.ttu.ee)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

//useful defines
#ifndef __GNUC__
//useful defines
#define STRICT
#define WIN32_LEAN_AND_MEAN
#endif

//standard includes
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>

//additional includes
#include "resource.h"

HINSTANCE hMainInstance;
HWND hMainWnd;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpszArgs, int nWinMode);
BOOL CALLBACK DialogFunc(HWND hdwnd, UINT Msg, WPARAM wParam, LPARAM lParam);

int WINAPI WinMain(HINSTANCE hInstance,
                   HINSTANCE hPrevInstance,
                   LPSTR lpszArgs,
                   int nWinMode)
{
  hMainInstance=hInstance;
  int nRes=DialogBox(hMainInstance, MAKEINTRESOURCE(IDD_DIALOG_MAIN), 0, DialogFunc);
  return 0;
}

BOOL CALLBACK DialogFunc(HWND hdwnd, UINT Msg, WPARAM wParam, LPARAM lParam)
{
  switch(Msg)
  {
    case WM_INITDIALOG:
    {
      //modify system menu
      //(remove Restore, Maximize and Resize entries)
      HMENU hSysMenu=GetSystemMenu(hdwnd, FALSE);
      DeleteMenu(hSysMenu, 0, MF_BYPOSITION);
      DeleteMenu(hSysMenu, 1, MF_BYPOSITION);
      DeleteMenu(hSysMenu, 2, MF_BYPOSITION);
      hMainWnd=hdwnd;

      //set dialog icon
      SetClassLong(hdwnd, GCL_HICON, (LONG)LoadIcon(NULL, IDI_APPLICATION));

      //select meters radio button
      CheckRadioButton(hdwnd, IDC_RADIOM, IDC_RADIOFT, IDC_RADIOM);

      //fill source edit box
      SetDlgItemText(hdwnd, IDC_EDITSRC, "1");
      
      //clear result edit box
      SetDlgItemText(hdwnd, IDC_EDITRES, "");

      return TRUE;
    }

    case WM_COMMAND:
      switch(LOWORD(wParam))
      {
        case IDCANCEL:
        case IDOK:
          //that way ESC and ENTER keys won't terminate our application
          return FALSE;

        //compute result depending on radio buttons state
        case IDCOMPUTE:
        {
          char pBuf[15];
          //get source edit box text
          UINT uRes=GetDlgItemText(hdwnd, IDC_EDITSRC, pBuf, 14);

          //if no text then notify user
          if(!uRes)
          {
            MessageBox(hdwnd, "No source!", "Converter", MB_OK | MB_ICONEXCLAMATION);
            return TRUE;
          }

          //convert text to floating point
          double dSrc=atof(pBuf);
          if(IsDlgButtonChecked(hdwnd, IDC_RADIOFT))  //ft -> m radio button checked
            sprintf(pBuf, "%f", dSrc*0.3048);
          else
            sprintf(pBuf, "%f", dSrc/0.3048);         //m -> ft radio button checked

          //set result edit box text
          SetDlgItemText(hdwnd, IDC_EDITRES, pBuf);
          return TRUE;
        }

        case ID_OK:
          DestroyWindow(hdwnd);
          return TRUE;

        default:
          return FALSE;
      }

    case WM_DESTROY:
      EndDialog(hdwnd, 0);
      return TRUE;
  }
  return FALSE;
}

