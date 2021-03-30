import React from 'react';
import styles from './index.module.scss';
import {MediaHeight} from '../../../providers/chatplugin/chatPluginModel';
import {ImageWithFallback} from 'render/components/ImageWithFallback';

export type MediaRenderProps = {
  height: MediaHeight;
  contentInfo: {
    altText?: string;
    fileUrl: string;
    forceRefresh: boolean;
  };
};

export const Media = ({height, contentInfo: {altText, fileUrl}}: MediaRenderProps) => (
  <ImageWithFallback
    src={fileUrl}
    alt={altText}
    className={`${styles.mediaImage} ${
      height === MediaHeight.tall ? styles.tall : height === MediaHeight.medium ? styles.medium : styles.short
    }`}
  />
);
